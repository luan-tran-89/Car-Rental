package com.edu.miu.service.impl;

import com.edu.miu.domain.PaymentMethod;
import com.edu.miu.service.kafka.KafkaProducerService;
import com.edu.miu.enums.CardType;
import com.edu.miu.repository.PaymentMethodRepository;
import com.edu.miu.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    private static final Logger logger = LoggerFactory.getLogger(PaymentMethodServiceImpl.class);

    @Override
    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod) {
        Optional<PaymentMethod> existingPaymentMethod = Optional.ofNullable(paymentMethodRepository.findByCardNumber(paymentMethod.getCardNumber()));
        if (existingPaymentMethod.isPresent()) {
            logger.error("Attempt to create a PaymentMethod with an already existing card number: {}", paymentMethod.getCardNumber());
            throw new IllegalArgumentException("A PaymentMethod with this card number already exists.");
        }

        PaymentMethod savedPaymentMethod = paymentMethodRepository.save(paymentMethod);
        logger.info("Successfully created new payment method with ID: {}", savedPaymentMethod.getMethodId());

        kafkaProducerService.sendMessage("payment-topic", "New payment method added with ID: " + savedPaymentMethod.getMethodId());
        String notificationMessage = String.format("User with ID %s added a new payment method with ID: %s", savedPaymentMethod.getUserId(), savedPaymentMethod.getMethodId());
        kafkaProducerService.sendMessage("user-notification-topic", notificationMessage);

        return savedPaymentMethod;
    }

    @Override
    public PaymentMethod updatePaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod.getMethodId() == null || !paymentMethodRepository.existsById(paymentMethod.getMethodId())) {
            logger.error("Attempt to update a PaymentMethod that doesn't exist with ID: {}", paymentMethod.getMethodId());
            throw new IllegalArgumentException("Cannot update a PaymentMethod that doesn't exist.");
        }

        PaymentMethod updatedPaymentMethod = paymentMethodRepository.save(paymentMethod);
        logger.info("Successfully updated payment method with ID: {}", updatedPaymentMethod.getMethodId());

        return updatedPaymentMethod;
    }

    @Override
    public List<PaymentMethod> findByUserId(Integer userId) {
        return paymentMethodRepository.findByUserId(userId);
    }

    @Override
    public Optional<PaymentMethod> findById(Integer id) {
        return paymentMethodRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        if (!paymentMethodRepository.existsById(id)) {
            logger.warn("Attempt to delete non-existing PaymentMethod with ID: {}", id);
            return;
        }
        paymentMethodRepository.deleteById(id);
        logger.info("Deleted PaymentMethod with ID: {}", id);
    }

    @Override
    public Optional<PaymentMethod> findByCardNumber(String cardNumber) {
        return Optional.ofNullable(paymentMethodRepository.findByCardNumber(cardNumber));
    }

    @Override
    public List<PaymentMethod> findByCardType(CardType cardType) {
        return paymentMethodRepository.findByCardType(cardType);
    }
}
