package com.edu.miu.feign;

import com.edu.miu.dto.RentalDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rental-service", url = "${feign.client.url.rentalService}")
public interface RentalServiceFeignClient {

    @GetMapping("/api/rentals/{rentalId}")
    RentalDto getRentalById(@PathVariable("rentalId") Long rentalId);

    // Add other methods resembling the API endpoints of the rental-service as needed.
}
