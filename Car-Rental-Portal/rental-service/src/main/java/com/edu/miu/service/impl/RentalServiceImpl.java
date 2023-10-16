package com.edu.miu.service.impl;

import com.edu.miu.domain.Rental;
import com.edu.miu.domain.User;
import com.edu.miu.dto.CarDto;
import com.edu.miu.dto.CarRentalDto;
import com.edu.miu.dto.RentalDto;
import com.edu.miu.dto.UserDto;
import com.edu.miu.enums.CarStatus;
import com.edu.miu.enums.UserRole;
import com.edu.miu.enums.UserStatus;
import com.edu.miu.feign.CarFleetClient;
import com.edu.miu.feign.UserClient;
import com.edu.miu.publisher.CarRentalPublisher;
import com.edu.miu.repository.RentalRepository;
import com.edu.miu.service.RentalService;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements RentalService {

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


    private long daysBetween(Date d1, Date d2) {
        return (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
    }

    //    @Override
//    public boolean addPaymentMethod(Integer userId, PaymentMethod paymentMethod) {
//        // Your logic to add & validate payment method
//        // This might be dependent on how you've designed the system.
//        return true;
//    }
}
