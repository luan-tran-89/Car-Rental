package com.edu.miu.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VisaPaymentMethodDTO extends PaymentMethodDTO {
    private String visaSpecificField; // Replace with any specific fields you might have for Visa.
}
