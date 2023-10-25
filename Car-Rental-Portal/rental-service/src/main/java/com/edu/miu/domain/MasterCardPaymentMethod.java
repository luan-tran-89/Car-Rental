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
@DiscriminatorValue("MASTER_CARD") // This denotes the value for this specific subclass in the single table
public class MasterCardPaymentMethod extends PaymentMethod {

    // Similarly, you can add any MasterCard specific attributes here if needed
    // Like some additional validation or special promotions specific to MasterCard
}
