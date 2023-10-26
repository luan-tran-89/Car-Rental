package com.edu.miu.factory;

import com.edu.miu.domain.MasterCardPaymentMethod;
import com.edu.miu.domain.PaymentMethod;
import com.edu.miu.domain.VisaPaymentMethod;
import com.edu.miu.enums.CardType;
import com.edu.miu.dto.PaymentMethodDTO;

public class PaymentMethodFactory {

    public PaymentMethod paymentMethod(PaymentMethodDTO dto) {
        if (dto.getCardType() == CardType.VISA) {
            return new VisaPaymentMethod();
        } else if (dto.getCardType() == CardType.MASTER_CARD) {
            return new MasterCardPaymentMethod();
        } else {
            throw new IllegalArgumentException("Unsupported Card Type");
        }
    }
}
