package com.edu.miu.service.impl;

import com.edu.miu.domain.Payment;
import com.edu.miu.domain.PaymentMethod;
import com.edu.miu.dto.PaymentDTO;
import com.edu.miu.enums.PaymentStatus;
import com.edu.miu.repository.PaymentMethodRepository;
import com.edu.miu.repository.PaymentRepository;
import com.edu.miu.security.AuthHelper;
import com.edu.miu.service.PaymentService;
import com.edu.miu.service.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private AuthHelper authHelper;

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentDTO.getMethodId()).get();

        if (paymentMethod == null || paymentMethod.getUserId() != authHelper.getUserId()) {
            logger.error("Invalid payment method with ID: {}", paymentDTO.getMethodId());
            throw new IllegalArgumentException("Invalid payment method.");
        }

        // Ensure the payment method has a sufficient balance.
        if (paymentDTO.getPaymentAmount() > paymentMethod.getBalance()) {
            logger.error("Insufficient balance for payment method with ID: {}", paymentDTO.getMethodId());
            throw new IllegalArgumentException("Insufficient balance.");
        }

        Payment payment = PaymentDTO.toEntity(paymentDTO);
        payment.setPaymentMethod(paymentMethod);

        Payment savedPayment = paymentRepository.save(payment);

        logger.info("Successfully created new payment with ID: {}", savedPayment.getPaymentId());

        // Sending a Kafka message regarding the creation of a new payment
        kafkaProducerService.sendMessage("payment-topic", "New payment created with ID: " + savedPayment.getPaymentId());

        return PaymentDTO.toDto(savedPayment);
    }

    @Override
    public PaymentDTO updatePayment(Integer paymentId, PaymentDTO paymentDTO) {
        Payment payment = paymentRepository.findById(paymentId).get();

        if (payment == null) {
            logger.error("Attempt to update a Payment that doesn't exist with ID: {}", payment.getPaymentId());
            throw new IllegalArgumentException("Cannot update a Payment that doesn't exist.");
        }

        if (paymentDTO.getMethodId() != null && paymentDTO.getMethodId() != payment.getPaymentMethod().getMethodId()) {
            PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentDTO.getMethodId()).get();

            if (paymentMethod == null || paymentMethod.getUserId() != authHelper.getUserId()) {
                logger.error("Invalid payment method with ID: {}", paymentDTO.getMethodId());
                throw new IllegalArgumentException("Invalid payment method.");
            }

            payment.setPaymentMethod(paymentMethod);
        }

        if (paymentDTO.getPaymentAmount() > 0 && paymentDTO.getPaymentAmount() != payment.getPaymentAmount()) {
            payment.setPaymentAmount(paymentDTO.getPaymentAmount());
        }

        if (paymentDTO.getDescription() != null && paymentDTO.getDescription() != payment.getDescription()) {
            payment.setDescription(paymentDTO.getDescription());
        }

        if (paymentDTO.getStatus() != null && paymentDTO.getStatus() != payment.getStatus()) {
            payment.setStatus(paymentDTO.getStatus());
        }

        Payment updatedPayment = paymentRepository.save(payment);
        logger.info("Successfully updated payment with ID: {}", updatedPayment.getPaymentId());

        return PaymentDTO.toDto(updatedPayment);
    }

    @Override
    public PaymentDTO findPaymentById(Integer paymentId) {
        return PaymentDTO.toDto(paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found with ID: " + paymentId)));
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

    @Transactional  // This ensures atomicity.
    @Override
    public boolean processPayment(Integer paymentMethodId, Double amount) {
        Optional<PaymentMethod> optPaymentMethod = paymentMethodRepository.findById(paymentMethodId);

        if(optPaymentMethod.isEmpty()) {
            logger.error("Payment method with ID {} not found.", paymentMethodId);
            throw new IllegalArgumentException("Payment method not found.");
        }

        PaymentMethod paymentMethod = optPaymentMethod.get();

        // Record this transaction in the Payments table.
        Payment payment = new Payment();
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentAmount(amount);

        if(paymentMethod.getBalance() < amount) {
            logger.warn("Insufficient balance for payment method with ID: {}", paymentMethodId);
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            kafkaProducerService.sendMessage("payment-topic", "Payment failed due to insufficient balance for payment method with ID: " + paymentMethodId);
            return false;
        }

        // Deduct amount from payment method and update it.
        double newBalance = paymentMethod.getBalance() - amount;
        paymentMethod.setBalance(newBalance);
        paymentMethodRepository.save(paymentMethod);

        payment.setStatus(PaymentStatus.COMPLETED);
        paymentRepository.save(payment);

        logger.info("Payment processed successfully for payment method with ID: {}", paymentMethodId);
        kafkaProducerService.sendMessage("payment-topic", "Payment processed successfully for payment method with ID: " + paymentMethodId);

        return true;
    }


}


