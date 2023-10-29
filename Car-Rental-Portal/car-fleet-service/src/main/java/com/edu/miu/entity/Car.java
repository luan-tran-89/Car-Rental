package com.edu.miu.entity;

import com.edu.miu.enums.CarStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.List;

/**
 * @author gasieugru
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @Column(name = "status", columnDefinition = "ENUM('AVAILABLE', 'RESERVED', 'PICKED', 'UNDER_MAINTENANCE', 'DISABLED') default 'AVAILABLE'", nullable = false)
    private CarStatus status = CarStatus.AVAILABLE;

    @Column(name="fixed_cost", nullable = false)
    private Double fixedCost;

    @Column(name="cost_per_day", nullable = false)
    private Double costPerDay;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Maintenance> maintenances;
}
