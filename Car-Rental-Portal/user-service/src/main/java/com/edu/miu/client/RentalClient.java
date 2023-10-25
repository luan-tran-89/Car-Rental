package com.edu.miu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author gasieugru
 */
@FeignClient(name = "RENTAL-SERVICE")
public interface RentalClient {

    @GetMapping("/rentals/user/{userId}/active-rentals")
    boolean isUserCurrentlyRenting(@PathVariable("userId") Integer userId);

    @GetMapping("/rentals")
    List<Object> getAllRentals();

    @GetMapping("/rentals/user/{userId}")
    List<Object> getRentalsByUser(@PathVariable("userId") Integer userId);

    @GetMapping("/rentals/user/{userId}/reservations")
    List<Object> getReservations(@PathVariable("userId") Integer userId);

    @GetMapping("/rentals/car/{carId}")
    List<Object> getRentalsByCar(@PathVariable("carId") Integer carId);

}
