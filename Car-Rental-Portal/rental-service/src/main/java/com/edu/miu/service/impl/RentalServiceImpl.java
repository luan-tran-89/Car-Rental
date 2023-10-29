package com.edu.miu.service.impl;

import com.edu.miu.domain.Rental;
import com.edu.miu.domain.User;
import com.edu.miu.dto.*;
import com.edu.miu.enums.CarStatus;
import com.edu.miu.enums.UserRole;
import com.edu.miu.enums.UserStatus;
import com.edu.miu.feign.CarFleetClient;
import com.edu.miu.feign.PaymentServiceClient;
import com.edu.miu.feign.UserClient;
import com.edu.miu.publisher.CarRentalPublisher;
import com.edu.miu.repository.RentalRepository;
import com.edu.miu.service.KafkaProducerService;
import com.edu.miu.service.RentalService;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edu.miu.enums.TimeReport;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements RentalService {

    private static final Logger logger = LoggerFactory.getLogger(RentalServiceImpl.class);


    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CarRentalPublisher carRentalPublisher;

    @Autowired
    private CarFleetClient carFleetClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private PaymentServiceClient paymentServiceClient;


    private List<RentalDto> convertFromRental(List<Rental> rentals) {
        return rentals.stream()
                .map(r -> modelMapper.map(r, RentalDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalDto> findAllRentals() {
        return this.convertFromRental(rentalRepository.findAll());
    }

    @Override
    public List<RentalDto> getRentalsByCarId(Integer carId) {
        return this.convertFromRental(rentalRepository.findByCarId(carId));
    }

    @Override
    public List<RentalDto> getRentalsByUserId(Integer userId) {
        return this.convertFromRental(rentalRepository.findByUserId(userId));
    }

    @Override
    public Optional<Rental> findRentalById(Integer id) {
        return rentalRepository.findById(id);
    }


    private boolean validateInformation(CarDto car, UserDto user) {
        return car == null || car.getStatus() != CarStatus.AVAILABLE ||
                user == null || user.getUserRole() != UserRole.CUSTOMER ||
                user.getStatus() != UserStatus.ACTIVE;
    }

    @Override
    public Rental reserveCar(Integer carId, Integer userId, Date startDate, Date endDate) {
        // Check if the user already has an unpaid (reserved) rental.
        List<Rental> reservedRentals = rentalRepository.findByUserIdAndPaymentIdIsNull(userId);
        if (!reservedRentals.isEmpty()) {
            logger.warn("User with ID: {} attempted to make multiple reservations.", userId);
            kafkaProducerService.sendMessage("reservation-topic", "Reservation failed. User with ID: " + userId + " already has an active reservation.");
            throw new RuntimeException("You already have an active reservation. You cannot reserve another car until the current reservation is completed or cancelled.");
        }

        // Fetch car and user details using Feign Clients (or services).
        CarDto car = carFleetClient.getCarById(carId);
        UserDto user = userClient.getUserById(userId);

        // Check if the car is available for reservation.
        if (car.getStatus() != CarStatus.AVAILABLE) {
            throw new RuntimeException("Car is not available for reservation.");
        }

        // Check user's status and role.
        if (user.getStatus() != UserStatus.ACTIVE || user.getUserRole() != UserRole.CUSTOMER) {
            throw new RuntimeException("User is not eligible to reserve a car.");
        }

        // Create the reservation.
        Rental rental = new Rental();
        rental.setCarId(carId);
        rental.setUserId(userId);

        // Set the start date as the current date and the end date as 2 days from now.
        rental.setStartDate(startDate);
        Date twoDaysLater = new Date(startDate.getTime() + (2 * 24 * 60 * 60 * 1000));  // +2 days in milliseconds
        rental.setEndDate(twoDaysLater);

        // Initialize the total cost as 0 (since no payment has been made during reservation).
        rental.setTotalCost(0.0);

        // Save the reservation in the database.
        Rental savedRental = rentalRepository.save(rental);

        // Notify the car fleet system to change the car's status to RESERVED.
        carRentalPublisher.sendUpdatedCarMessage(new CarRentalDto(car.getCarId(), CarStatus.RESERVED));

        return savedRental;
    }


   /* @Override
    public Rental reserveCar(Integer carId, Integer userId, Date startDate, Date endDate) {
        try {
            CarDto car = carFleetClient.getCarById(carId);
            UserDto user = userClient.getUserById(userId);

            if (this.validateInformation(car, user)) {
                throw new RuntimeException("Error reserving car or fetching user details.");
            }

            Rental rental = new Rental();
            rental.setCarId(carId);
            rental.setUserId(userId);
            rental.setStartDate(startDate);
            rental.setEndDate(endDate);
            rental.setTotalCost(0.0); // Initialize with 0 or fetch from the existing value if it's already set.
            rentalRepository.save(rental);

            carRentalPublisher.sendUpdatedCarMessage(new CarRentalDto(car.getCarId(), CarStatus.RESERVED));

            return rental;

        } catch (FeignException e) {
            throw new RuntimeException("Error reserving car or fetching user details.", e);
        }
    }*/

    @Transactional
    @Override
    public boolean cancelReservation(Integer rentalId, Integer userId) {
        Optional<Rental> rentalOpt = rentalRepository.findById(rentalId);

        if (!rentalOpt.isPresent()) {
            logger.error("Rental not found with ID: {}", rentalId);
            throw new IllegalArgumentException("Rental not found with ID: " + rentalId);
        }

        Rental rental = rentalOpt.get();

        // Ensure that the person trying to cancel the reservation is indeed the one who made it.
        if (!rental.getUserId().equals(userId)) {
            logger.warn("Unauthorized attempt to cancel reservation with ID: {}. User: {}", rentalId, userId);
            throw new SecurityException("User does not have permission to cancel this reservation.");
        }

        // Change the car's status to AVAILABLE
        CarDto car = carFleetClient.getCarById(rental.getCarId());
        carRentalPublisher.sendUpdatedCarMessage(new CarRentalDto(car.getCarId(), CarStatus.AVAILABLE));

        // Delete the reservation.
        rentalRepository.deleteById(rentalId);

        logger.info("User {} successfully canceled reservation with ID: {}", userId, rentalId);
        kafkaProducerService.sendMessage("reservation-topic", "Reservation with ID: " + rentalId + " was canceled by user " + userId);

        return true;
    }

    @Override
    public Rental finalizeRental(Integer rentalId, Integer paymentMethodId) {
        // Fetch the existing reservation.
        Rental rental = rentalRepository.findById(rentalId).orElseThrow(() -> new RuntimeException("Rental not found"));

        // Fetch the car details.
        CarDto car = carFleetClient.getCarById(rental.getCarId());

        // Fetch the user details.
        UserDto user = userClient.getUserById(rental.getUserId());

        // Calculate the total cost based on the days between the start and end date.
        double totalCost = car.getFixedCost() + car.getCostPerDay() * daysBetween(rental.getStartDate(), rental.getEndDate());

        if (user.getUserRole() == UserRole.FREQUENT_RENTER) {
            totalCost = totalCost * (1 - user.getFrequentRenterType().getDiscount());
        }

        // Deduct the amount from the payment method.
        // Here we assume the payment-service will handle the actual payment and balance deduction.
        paymentServiceClient.processPayment(paymentMethodId, totalCost);

        // Update the rental object with the total cost.
        rental.setTotalCost(totalCost);
        rentalRepository.save(rental);

        // Change the car's status to PICKED.
        carRentalPublisher.sendUpdatedCarMessage(new CarRentalDto(car.getCarId(), CarStatus.PICKED));

        return rental;
    }

    @Override
    public Rental directBooking(Integer carId, Integer userId, Date startDate, Date endDate, Integer paymentMethodId) {
        try {
            // Fetch the car details.
            CarDto car = carFleetClient.getCarById(carId);
            UserDto user = userClient.getUserById(userId);

            // Check if the car is available for booking.
            if (car.getStatus() != CarStatus.AVAILABLE) {
                throw new RuntimeException("Car is not available for direct booking.");
            }

            // Check user's status and role.
            if (user.getStatus() != UserStatus.ACTIVE || user.getUserRole() != UserRole.CUSTOMER) {
                throw new RuntimeException("User is not eligible to directly book a car.");
            }

            // Calculate the total cost based on the days between the start and end date.
            double totalCost = car.getFixedCost() + car.getCostPerDay() * daysBetween(startDate, endDate);

            if (user.getUserRole() == UserRole.FREQUENT_RENTER) {
                totalCost = totalCost * (1 - user.getFrequentRenterType().getDiscount());
            }

            // Create the booking.
            Rental rental = new Rental();
            rental.setCarId(carId);
            rental.setUserId(userId);
            rental.setStartDate(startDate);
            rental.setEndDate(endDate);
            rental.setTotalCost(totalCost);

            // Process the payment.
            paymentServiceClient.processPayment(paymentMethodId, totalCost);

            // Save the rental in the database.
            Rental savedRental = rentalRepository.save(rental);

            // Change the car's status to PICKED.
            carRentalPublisher.sendUpdatedCarMessage(new CarRentalDto(car.getCarId(), CarStatus.PICKED));

            return savedRental;

        } catch (FeignException e) {
            throw new RuntimeException("Error during direct booking due to issues fetching car or user details.", e);
        }
    }

    @Override
    public Rental createRental(Rental rental) {
        try {
            CarDto car = carFleetClient.getCarById(rental.getCarId());
            UserDto user = userClient.getUserById(rental.getUserId());

            if (this.validateInformation(car, user)) {
                throw new RuntimeException("Error reserving car or fetching user details.");
            }

            double totalCost = car.getFixedCost() + car.getCostPerDay() * daysBetween(rental.getStartDate(), rental.getEndDate());

            if (user.getUserRole() == UserRole.FREQUENT_RENTER) {
                totalCost = totalCost * (1 - user.getFrequentRenterType().getDiscount());
            }

            rental.setTotalCost(totalCost);

            return rentalRepository.save(rental);

        } catch (FeignException e) {
            throw new RuntimeException("Error creating rental due to issues fetching car or user details.", e);
        }
    }

    @Override
    public Rental updateRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    @Override
    public void deleteRental(Integer id) {
        Optional<Rental> rental = rentalRepository.findById(id);
        if (rental.isPresent()) {
            CarDto car = carFleetClient.getCarById(rental.get().getCarId());
//            car.setStatus(CarStatus.AVAILABLE);
            rentalRepository.deleteById(id);

            carRentalPublisher.sendUpdatedCarMessage(new CarRentalDto(car.getCarId(), CarStatus.AVAILABLE));
        }
    }

    @Override
    public boolean completeRental(Integer rentalId) {
        Optional<Rental> optionalRental = rentalRepository.findById(rentalId);
        if (optionalRental.isPresent()) {
            Rental rental = optionalRental.get();
            CarDto car = carFleetClient.getCarById(rental.getCarId());
//            car.setStatus(CarStatus.PICKED);

            carRentalPublisher.sendUpdatedCarMessage(new CarRentalDto(car.getCarId(), CarStatus.PICKED));
            return true;
        }
        return false;
    }

    @Override
    public boolean isUserCurrentlyRenting(Integer userId) {
        Date today = new Date();
        List<Rental> activeRentals = rentalRepository.findByUserIdAndEndDateAfter(userId, today);
        return !activeRentals.isEmpty();
    }

 /*   @Override
    public List<RentalDto> getReservationsByUser(Integer userId) {
        List<Rental> activeRentals = rentalRepository.findByUserIdAndEndDateAfter(userId, new Date());
        return this.convertFromRental(activeRentals);
    }
*/
 @Override
 public List<RentalDto> getReservationsByUser(Integer userId) {
     List<Rental> activeRentals = rentalRepository.findByUserIdAndStartDateNotBefore(userId, new Date());
     return this.convertFromRental(activeRentals);
 }


    private long daysBetween(Date d1, Date d2) {
        return (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
    }


    @Override
    public boolean addPaymentMethod(Integer userId, PaymentMethodDTO paymentMethodDetails) {
        try {
            // Set the userId in the paymentMethodDetails DTO since it might not be set yet
            paymentMethodDetails.setUserId(userId);

            // Call the payment-service to add the payment method for the user
            PaymentMethodDTO addedPaymentMethod = paymentServiceClient.addPaymentMethod(paymentMethodDetails);

            // Here, we're assuming the payment-service returns the added payment method or throws an error if it fails.
            // You can modify this according to your payment-service's actual response.
            if (addedPaymentMethod != null && addedPaymentMethod.getMethodId() != null) {
                logger.info("Payment method added successfully for user ID: {}", userId);
                return true;
            } else {
                logger.error("Failed to add payment method for user ID: {}", userId);
                return false;
            }

        } catch (FeignException e) {
            logger.error("Error adding payment method for user ID: {}. Error: {}", userId, e.getMessage());
            throw new RuntimeException("Error while adding payment method.", e);
        }
    }

    @Override
    public List<RentalDto> fetchRentalsByTimeReport(ReportFilter reportFilter) {
        Calendar cal = Calendar.getInstance();

        Date startDate;
        Date endDate;
        switch (reportFilter.getTimeReport()) {
            case MONTHLY:
                // set the year and month from the ReportFilter
                cal.set(Calendar.YEAR, reportFilter.getYear());
                cal.set(Calendar.MONTH, reportFilter.getMonth() - 1);  // month is 0-based
                cal.set(Calendar.DAY_OF_MONTH, 1);
                startDate = cal.getTime();

                cal.add(Calendar.MONTH, 1);
                cal.add(Calendar.DAY_OF_MONTH, -1);
                endDate = cal.getTime();
                break;
            case QUARTERLY:
                // set the year and quarter from the ReportFilter
                int startMonth = (reportFilter.getQuarter() - 1) * 3;
                cal.set(Calendar.YEAR, reportFilter.getYear());
                cal.set(Calendar.MONTH, startMonth);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                startDate = cal.getTime();

                cal.add(Calendar.MONTH, startMonth + 2);
                cal.add(Calendar.DAY_OF_MONTH, -1);
                endDate = cal.getTime();
                break;
            case ANNUAL:
                // set the year from the ReportFilter
                cal.set(Calendar.YEAR, reportFilter.getYear());
                cal.set(Calendar.DAY_OF_YEAR, 1);
                startDate = cal.getTime();

                cal.add(Calendar.YEAR, 1);
                cal.add(Calendar.DAY_OF_YEAR, -1);
                endDate = cal.getTime();
                break;
            default:
                throw new IllegalArgumentException("Unsupported Time Report");
        }

        return this.convertFromRental(rentalRepository.findByStartDateBetween(startDate, endDate));
    }


}

