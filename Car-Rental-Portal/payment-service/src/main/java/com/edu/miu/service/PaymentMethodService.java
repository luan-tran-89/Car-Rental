package com.edu.miu.service;

import com.edu.miu.domain.PaymentMethod;
import com.edu.miu.dto.BusinessException;
import com.edu.miu.dto.PaymentMethodDTO;
import com.edu.miu.enums.CardType;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodService {

    // Create a new payment method
    PaymentMethodDTO createPaymentMethod(PaymentMethodDTO paymentMethod) throws BusinessException;

    // Update an existing payment method
    PaymentMethodDTO updatePaymentMethod(Integer methodId, PaymentMethodDTO paymentMethod) throws BusinessException;

    // Retrieve a specific payment method by ID
    Optional<PaymentMethodDTO> findById(Integer methodId);

    // Delete a payment method by ID
    void deleteById(Integer methodId);

    // Retrieve all payment methods for a specific user
    List<PaymentMethodDTO> findByUserId(Integer userId);

    Optional<PaymentMethodDTO> findByCardNumber(String cardNumber);

    // Retrieve all payment methods for a specific Card Type
    List<PaymentMethodDTO> findByCardType(CardType cardType);

    // Other methods as required by your business logic...
}
