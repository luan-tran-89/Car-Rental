package com.edu.miu.model;

import com.edu.miu.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author gasieugru
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodRequest {

    private String cardNumber;

    private Date expiredDate;

    private CardType cardType;

    private int cvv;

}
