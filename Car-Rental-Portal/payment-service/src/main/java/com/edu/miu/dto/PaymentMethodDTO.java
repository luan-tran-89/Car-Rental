package com.edu.miu.dto;

import com.edu.miu.domain.PaymentMethod;
import com.edu.miu.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodDTO {

    private Integer methodId;

    private CardType cardType;

    private String cardNumber;

    private String expiryDate;

    private String cvv;

    private Double approvalAmount = 0.0;

    private Double usedAmount = 0.0;

    private Double totalUsed = 0.0;

    private Double balance = 0.0;

    private Integer userId;
    // You can add any other necessary fields that you might need to transfer between layers.


    public static PaymentMethodDTO toDto(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            return null;
        }

        String cvv = "**" + paymentMethod.getCvv().substring(0, 1);
        String cardNumber = "******" + paymentMethod.getCardNumber().substring(6);

        return PaymentMethodDTO.builder()
                .methodId(paymentMethod.getMethodId())
                .cardNumber(cardNumber)
                .expiryDate(paymentMethod.getExpiryDate())
                .cvv(cvv)
                .cardType(paymentMethod.getCardType())
                .approvalAmount(paymentMethod.getApprovalAmount())
                .usedAmount(paymentMethod.getUsedAmount())
                .totalUsed(paymentMethod.getTotalUsed())
                .balance(paymentMethod.getBalance())
                .userId(paymentMethod.getUserId())
                .build();
    }
}
