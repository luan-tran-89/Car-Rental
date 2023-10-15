package com.edu.miu.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rentalId;

    @Column(name = "car_id", nullable = false)
    private Integer carId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(name = "payment_id")
    private Integer paymentId;

    // If you need to store the calculated rental cost:
    @Column(name = "total_cost")
    private Double totalCost;

    // Default constructor
    public Rental() {}

    // Parameterized constructor
    public Rental(Integer carId, Integer userId, Date startDate, Date endDate, Integer paymentId) {
        this.carId = carId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentId = paymentId;
    }

    // Add any business logic or additional methods if necessary

    // ... (any other logic or methods pertaining to the Rental class)

}
