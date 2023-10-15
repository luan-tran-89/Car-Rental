package rentalservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import rentalservice.domain.Car;
import rentalservice.enums.CarStatus;

@FeignClient(name = "car-fleet-service")
public interface CarFleetClient {

    @GetMapping("/cars/{id}")
    Car getCarById(@PathVariable("id") Integer id);

    @PutMapping("/cars/{id}/status")
    void updateCarStatus(@PathVariable("id") Integer id, @RequestBody CarStatus status);

}
