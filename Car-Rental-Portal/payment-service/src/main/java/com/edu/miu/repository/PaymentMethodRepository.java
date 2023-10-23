package com.edu.miu.repository;

import com.edu.miu.domain.PaymentMethod;
import com.edu.miu.enums.CardType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {

    // Fetch all payment methods associated with a particular user
    List<PaymentMethod> findByUserId(Integer userId);

    // Fetch a specific payment method by card number (can be used for validation/checking duplicates)
    PaymentMethod findByCardNumber(String cardNumber);

    // Fetch by card type (could be used for analytical purposes or user preference-based promotions)
    List<PaymentMethod> findByCardType(CardType cardType);

    // Additional potential methods based on the use cases:

    // Fetch payment methods that have a balance below a certain threshold (e.g., for sending reminders)
    List<PaymentMethod> findByBalanceLessThan(Double amount);

    // Fetch payment methods with a specific approval amount
    List<PaymentMethod> findByApprovalAmount(Double amount);
}
