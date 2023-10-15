package com.edu.miu.service.impl;

import com.edu.miu.domain.Car;
import com.edu.miu.domain.Rental;
import com.edu.miu.domain.User;
import com.edu.miu.enums.CarStatus;
import com.edu.miu.enums.UserRole;
import com.edu.miu.feign.CarFleetClient;
import com.edu.miu.feign.UserClient;
import com.edu.miu.repository.RentalRepository;
import com.edu.miu.service.KafkaProducerService;
import com.edu.miu.service.RentalService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private CarFleetClient carFleetClient;

    @Autowired
    private UserClient userClient;

    @Override
    public List<Rental> findAllRentals() {
        return rentalRepository.findAll();
    }

    @Override
    public Optional<Rental> findRentalById(Integer id) {
        return rentalRepository.findById(id);
    }

    @Override
    public Rental reserveCar(Integer carId, Integer userId, Date startDate, Date endDate) {
        try {
            Car car = carFleetClient.getCarById(carId);
            User user = userClient.getUserById(userId);

            car.setStatus(CarStatus.RESERVED);

            String message = "{\"carId\":" + car.getCarId() + ", \"status\":\"RESERVED\"}";
            kafkaProducerService.sendMessage("carStatusTopic", message);

            Rental rental = new Rental();
            rental.setCarId(carId);
            rental.setUserId(userId);
            rental.setStartDate(startDate);
            rental.setEndDate(endDate);
            rental.setTotalCost(0.0); // Initialize with 0 or fetch from the existing value if it's already set.

            return rentalRepository.save(rental);

        } catch (FeignException e) {
            throw new RuntimeException("Error reserving car or fetching user details.", e);
        }
    }

    @Override
    public Rental createRental(Rental rental) {
        try {
            Car car = carFleetClient.getCarById(rental.getCarId());
            User user = userClient.getUserById(rental.getUserId());

            double totalCost = car.getFixedCost() + car.getCostPerDay() * daysBetween(rental.getStartDate(), rental.getEndDate());

            if (user.getRole() == UserRole.FREQUENT_RENTER) {
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
            Car car = carFleetClient.getCarById(rental.get().getCarId());
            car.setStatus(CarStatus.AVAILABLE);

            String message = "{\"carId\":" + car.getCarId() + ", \"status\":\"AVAILABLE\"}";
            kafkaProducerService.sendMessage("carStatusTopic", message);

            rentalRepository.deleteById(id);
        }
    }

    @Override
    public boolean completeRental(Integer rentalId) {
        Optional<Rental> optionalRental = rentalRepository.findById(rentalId);
        if (optionalRental.isPresent()) {
            Rental rental = optionalRental.get();
            Car car = carFleetClient.getCarById(rental.getCarId());
            car.setStatus(CarStatus.PICKED);

            String message = "{\"carId\":" + car.getCarId() + ", \"status\":\"PICKED\"}";
            kafkaProducerService.sendMessage("carStatusTopic", message);

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
