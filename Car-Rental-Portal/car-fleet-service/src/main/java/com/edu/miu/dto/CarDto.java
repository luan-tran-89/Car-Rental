package com.edu.miu.dto;

import com.edu.miu.enums.CarStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gasieugru
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {

    private Integer carId;

    private String model;

    private String make;

    private CarStatus status;

    private Double baseCost;

    private Double perDayCost;
}
