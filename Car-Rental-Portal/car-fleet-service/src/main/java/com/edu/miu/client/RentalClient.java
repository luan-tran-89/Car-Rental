package com.edu.miu.client;

import com.edu.miu.enums.TimeReport;
import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author gasieugru
 */
@FeignClient(name = "RENTAL-SERVICE")
public interface RentalClient {

    @GetMapping("/rentals")
    List<Object> getAllRentals();

    @GetMapping("/rental-report")
    List<Object> getRentalReport(@RequestParam("timeReport") TimeReport timeReport);

    @GetMapping("/rentals/user/{userId}")
    List<Object> getRentalsByUser(@PathVariable("userId") Integer userId);

    @GetMapping("/rentals/car/{userId}")
    List<Object> getRentalsByCar(@PathVariable("carId") Integer carId);

    @GetMapping
    List<Object> getReservations(@PathVariable("userId") Integer userId);

    @GetMapping("/rentals/user/{userId}/active-rentals")
    boolean isUserCurrentlyRenting(@PathVariable("userId") Integer userId);

}
