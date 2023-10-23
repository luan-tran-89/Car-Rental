package com.edu.miu.dto;

import com.edu.miu.enums.CardType;
import lombok.Data;

@Data
public class PaymentMethodDTO {
    private Integer methodId;
    private CardType cardType;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private Double approvalAmount;
    private Double totalUsed;
    private Double balance;
    private Integer userId;
    // You can add any other necessary fields that you might need to transfer between layers.
}
