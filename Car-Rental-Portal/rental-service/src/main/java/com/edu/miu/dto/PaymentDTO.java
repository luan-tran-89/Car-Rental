package com.edu.miu.dto;

import com.edu.miu.enums.PaymentStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentDTO {

    private Integer paymentId;

    private Integer methodId;

    private Double paymentAmount;

    private LocalDate transactionDate;

    private String description;

    private PaymentStatus status;
}
