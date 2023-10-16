package com.edu.miu.feign;

import com.edu.miu.dto.CarDto;
import com.edu.miu.enums.CarStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "car-fleet-service")
public interface CarFleetClient {

    @GetMapping("/car-fleet/car/{id}")
    CarDto getCarById(@PathVariable("id") Integer id);

    @PutMapping("/cars/{id}/status")
    void updateCarStatus(@PathVariable("id") Integer id, @RequestBody CarStatus status);

}
