package com.edu.miu.service.impl;

import com.edu.miu.domain.Payment;
import com.edu.miu.enums.PaymentStatus;
import com.edu.miu.repository.PaymentRepository;
import com.edu.miu.service.PaymentService;
import com.edu.miu.service.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Override
    public Payment createPayment(Payment payment) {
        // Ensure the payment method has a sufficient balance.
        if (payment.getPaymentAmount() > payment.getPaymentMethod().getBalance()) {
            logger.error("Insufficient balance for payment method with ID: {}", payment.getPaymentMethod().getMethodId());
            throw new IllegalArgumentException("Insufficient balance.");
        }

        Payment savedPayment = paymentRepository.save(payment);

        logger.info("Successfully created new payment with ID: {}", savedPayment.getPaymentId());

        // Sending a Kafka message regarding the creation of a new payment
        kafkaProducerService.sendMessage("payment-topic", "New payment created with ID: " + savedPayment.getPaymentId());

        return savedPayment;
    }

    @Override
    public Payment updatePayment(Payment payment) {
        if (payment.getPaymentId() == null || !paymentRepository.existsById(payment.getPaymentId())) {
            logger.error("Attempt to update a Payment that doesn't exist with ID: {}", payment.getPaymentId());
            throw new IllegalArgumentException("Cannot update a Payment that doesn't exist.");
        }

        Payment updatedPayment = paymentRepository.save(payment);
        logger.info("Successfully updated payment with ID: {}", updatedPayment.getPaymentId());

        return updatedPayment;
    }

    @Override
    public Payment findPaymentById(Integer paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(() -> new IllegalArgumentException("Payment not found with ID: " + paymentId));
    }

    @Override
    public List<Payment> findPaymentsByUserId(Integer userId) {
        return paymentRepository.findByPaymentMethod_UserId(userId);
    }

    @Override
    public void deletePaymentById(Integer paymentId) {
        if (!paymentRepository.existsById(paymentId)) {
            logger.warn("Attempt to delete non-existing Payment with ID: {}", paymentId);
            return;
        }

        paymentRepository.deleteById(paymentId);
        logger.info("Deleted Payment with ID: {}", paymentId);
    }

    @Override
    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public List<Payment> findByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }
}


/*
package com.edu.miu.service.impl;

import com.edu.miu.domain.Payment;
import com.edu.miu.enums.PaymentStatus;
import com.edu.miu.repository.PaymentRepository;
import com.edu.miu.service.PaymentService;
import com.edu.miu.service.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Override
    public Payment createPayment(Payment payment) {
        // Ensure the payment method has a sufficient balance.
        if (payment.getPaymentAmount() > payment.getPaymentMethod().getBalance()) {
            logger.error("Insufficient balance for payment method with ID: {}", payment.getPaymentMethod().getMethodId());
            throw new IllegalArgumentException("Insufficient balance.");
        }

        Payment savedPayment = paymentRepository.save(payment);

        logger.info("Successfully created new payment with ID: {}", savedPayment.getPaymentId());

        // Sending a Kafka message regarding the creation of a new payment
        kafkaProducerService.sendMessage("payment-topic", "New payment created with ID: " + savedPayment.getPaymentId());

        return savedPayment;
    }

    @Override
    public Payment updatePayment(Payment payment) {
        if (payment.getPaymentId() == null || !paymentRepository.existsById(payment.getPaymentId())) {
            logger.error("Attempt to update a Payment that doesn't exist with ID: {}", payment.getPaymentId());
            throw new IllegalArgumentException("Cannot update a Payment that doesn't exist.");
        }

        Payment updatedPayment = paymentRepository.save(payment);
        logger.info("Successfully updated payment with ID: {}", updatedPayment.getPaymentId());

        return updatedPayment;
    }

    @Override
    public Optional<Payment> findById(Integer paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> findByPaymentMethodUserId(Integer userId) {
        return paymentRepository.findByPaymentMethod_UserId(userId);
    }

    @Override
    public void deleteById(Integer paymentId) {
        if (!paymentRepository.existsById(paymentId)) {
            logger.warn("Attempt to delete non-existing Payment with ID: {}", paymentId);
            return;
        }

        paymentRepository.deleteById(paymentId);
        logger.info("Deleted Payment with ID: {}", paymentId);
    }

    @Override
    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public List<Payment> findByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }
}
*/
