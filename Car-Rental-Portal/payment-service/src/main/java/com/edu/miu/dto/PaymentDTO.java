package com.edu.miu.dto;

import com.edu.miu.domain.Payment;
import com.edu.miu.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    private Integer paymentId;

    private Integer methodId;

    private Double paymentAmount;

    private LocalDate transactionDate;

    private String description;

    private PaymentStatus status;

    public static Payment toEntity(PaymentDTO paymentDTO) {
        if (paymentDTO == null) {
            return null;
        }

        return Payment.builder()
                .paymentAmount(paymentDTO.getPaymentAmount())
                .transactionDate(paymentDTO.getTransactionDate())
                .description(paymentDTO.getDescription())
                .status(paymentDTO.getStatus())
                .build();
    }

    public static PaymentDTO toDto(Payment payment) {
        if (payment == null) {
            return null;
        }

        return PaymentDTO.builder()
                .paymentId(payment.getPaymentId())
                .methodId(payment.getPaymentMethod().getMethodId())
                .paymentAmount(payment.getPaymentAmount())
                .transactionDate(payment.getTransactionDate())
                .description(payment.getDescription())
                .status(payment.getStatus())
                .build();
    }
}
