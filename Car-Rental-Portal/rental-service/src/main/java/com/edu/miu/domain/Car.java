package com.edu.miu.domain;

import com.edu.miu.enums.CarStatus;
import lombok.Data;

import javax.persistence.*;
@Data
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;
    private String model;
    private String make;
    @Enumerated(EnumType.STRING)
    private CarStatus status;
    private Double fixedCost;  // Moved this before costPerDay
    private Double costPerDay;
    private String image;


}
