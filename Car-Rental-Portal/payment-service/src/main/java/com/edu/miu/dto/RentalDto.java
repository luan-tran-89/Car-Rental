package com.edu.miu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {

    private Integer rentalId;

    private Integer carId;

    private Integer userId;

    private Date startDate;

    private Date endDate;

    private Integer paymentId;

    private Double totalCost;
}
