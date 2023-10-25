package com.edu.miu.service;

import com.edu.miu.domain.Payment;
import com.edu.miu.enums.PaymentStatus;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    Payment createPayment(Payment payment);

    Payment updatePayment(Payment payment);

    Payment findPaymentById(Integer paymentId); // changed from findById to findPaymentById

    List<Payment> findPaymentsByUserId(Integer userId); // changed from findByPaymentMethodUserId to findPaymentsByUserId

    void deletePaymentById(Integer paymentId); // changed from deleteById to deletePaymentById

    List<Payment> findAllPayments();

    List<Payment> findByStatus(PaymentStatus status);

    boolean processPayment(Integer paymentMethodId, Double amount);

}
