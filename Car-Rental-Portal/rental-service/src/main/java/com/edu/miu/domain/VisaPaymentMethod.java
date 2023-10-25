package com.edu.miu.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("VISA") // This denotes the value for this specific subclass in the single table
public class VisaPaymentMethod extends PaymentMethod {

    // You can add any VISA specific attributes here if needed in the future
    // For example, some additional validation or special promotions specific to VISA cards
}
