package com.edu.miu.converters;

import com.edu.miu.domain.Payment;
import com.edu.miu.dto.PaymentDTO;

public class PaymentConverter {

    // Convert Payment entity to PaymentDTO
    public static PaymentDTO toDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentId(payment.getPaymentId());

        if(payment.getPaymentMethod() != null) {
            paymentDTO.setMethodId(payment.getPaymentMethod().getMethodId());
        }

        paymentDTO.setPaymentAmount(payment.getPaymentAmount());
        paymentDTO.setTransactionDate(payment.getTransactionDate());
        paymentDTO.setDescription(payment.getDescription());
        paymentDTO.setStatus(payment.getStatus());
        // ... any other fields if needed

        return paymentDTO;
    }

    // Convert PaymentDTO to Payment entity
    public static Payment toEntity(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setPaymentId(paymentDTO.getPaymentId());
        // Note: We will NOT set the PaymentMethod object in this converter
        // because we shouldn't set relations based on IDs directly in the converter.
        payment.setPaymentAmount(paymentDTO.getPaymentAmount());
        payment.setTransactionDate(paymentDTO.getTransactionDate());
        payment.setDescription(paymentDTO.getDescription());
        payment.setStatus(paymentDTO.getStatus());
        // ... any other fields if needed

        return payment;
    }
}
