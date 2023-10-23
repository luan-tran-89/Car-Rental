package com.edu.miu.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MasterCardPaymentMethodDTO extends PaymentMethodDTO {
    private String masterCardSpecificField; // Replace with any specific fields you might have for MasterCard.
}
