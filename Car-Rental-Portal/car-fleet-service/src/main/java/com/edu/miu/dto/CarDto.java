package com.edu.miu.dto;

import com.edu.miu.enums.CarStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gasieugru
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDto {

    private Integer carId;

    private String model;

    private String make;

    private String image;

    private CarStatus status;

    private Double fixedCost;

    private Double costPerDay;

}
