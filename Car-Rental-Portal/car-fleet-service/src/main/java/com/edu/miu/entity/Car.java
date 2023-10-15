package com.edu.miu.entity;

import com.edu.miu.enums.CarStatus;
import lombok.Data;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.List;

/**
 * @author gasieugru
 */

@Entity
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String make;

    private String image;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarStatus status = CarStatus.AVAILABLE;

    @Column(name="base_code", nullable = false)
    private Double baseCost;

    @Column(name="per_day_cost", nullable = false)
    private Double perDayCost;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Maintenance> maintenances;
}
