package com.edu.miu.service.impl;

import com.edu.miu.client.RentalClient;
import com.edu.miu.service.CarRentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gasieugru
 */
@Service
@RequiredArgsConstructor
public class CarRentalServiceImpl implements CarRentalService {

    private final RentalClient rentalClient;

    private final CircuitBreakerFactory breakerFactory;

    @Override
    public boolean isUserCurrentlyRenting(Integer userId) {
        CircuitBreaker circuitBreaker = breakerFactory.create("check-renting-fetching");
        boolean isUserCurrentlyRenting = circuitBreaker.run(() -> rentalClient.isUserCurrentlyRenting(userId), throwable -> null);
//        return  rentalClient.isUserCurrentlyRenting(userId);
        return isUserCurrentlyRenting;
    }

    @Override
    public List<Object> getCurrentReservations(int userId) {
        CircuitBreaker circuitBreaker = breakerFactory.create("reservations-fetching");
        List<Object> data = circuitBreaker.run(() -> rentalClient.getReservations(userId), throwable -> null);
//        List<Object> data = rentalClient.getReservations(userId);
        return data == null ? new ArrayList<>() : data;
    }

    @Override
    public List<Object> getAllRentalHistory() {
        CircuitBreaker circuitBreaker = breakerFactory.create("rentals-fetching");
        List<Object> data = circuitBreaker.run(() -> rentalClient.getAllRentals(), throwable -> null);
//        List<Object> data = rentalClient.getAllRentals();
        return data == null ? new ArrayList<>() : data;
    }

    @Override
    public List<Object> getRentalHistory(int userId) {
        CircuitBreaker circuitBreaker = breakerFactory.create("rental-history-fetching");
        List<Object> data = circuitBreaker.run(() -> rentalClient.getRentalsByUser(userId), throwable -> null);
//        List<Object> data = rentalClient.getRentalsByUser(userId);
        return data == null ? new ArrayList<>() : data;
    }

    @Override
    public List<Object> getRentalHistoryByCar(int carId) {
        CircuitBreaker circuitBreaker = breakerFactory.create("car-rental-history-fetching");
        List<Object> data = circuitBreaker.run(() -> rentalClient.getRentalsByCar(carId), throwable -> null);
//        List<Object> data = rentalClient.getRentalsByCar(carId);
        return data == null ? new ArrayList<>() : data;
    }
}
