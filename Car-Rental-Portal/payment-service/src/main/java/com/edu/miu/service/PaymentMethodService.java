package com.edu.miu.service;

import com.edu.miu.domain.PaymentMethod;
import com.edu.miu.enums.CardType;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodService {

    // Create a new payment method
    PaymentMethod createPaymentMethod(PaymentMethod paymentMethod);

    // Update an existing payment method
    PaymentMethod updatePaymentMethod(PaymentMethod paymentMethod);

    // Retrieve a specific payment method by ID
    Optional<PaymentMethod> findById(Integer methodId);

    // Delete a payment method by ID
    void deleteById(Integer methodId);

    // Retrieve all payment methods for a specific user
    List<PaymentMethod> findByUserId(Integer userId);

    Optional<PaymentMethod> findByCardNumber(String cardNumber);

    // Retrieve all payment methods for a specific Card Type
    List<PaymentMethod> findByCardType(CardType cardType);

    // Other methods as required by your business logic...
}
