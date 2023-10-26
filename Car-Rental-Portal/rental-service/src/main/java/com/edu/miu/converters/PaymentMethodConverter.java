package com.edu.miu.converters;

import com.edu.miu.domain.MasterCardPaymentMethod;
import com.edu.miu.domain.PaymentMethod;
import com.edu.miu.domain.VisaPaymentMethod;
import com.edu.miu.dto.PaymentMethodDTO;
import com.edu.miu.enums.CardType;

public class PaymentMethodConverter {

    // Convert PaymentMethod entity to PaymentMethodDTO
    public static PaymentMethodDTO toDTO(PaymentMethod paymentMethod) {
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
        paymentMethodDTO.setMethodId(paymentMethod.getMethodId());
        paymentMethodDTO.setUserId(paymentMethod.getUserId());
        paymentMethodDTO.setCardNumber(paymentMethod.getCardNumber());
        paymentMethodDTO.setExpiryDate(paymentMethod.getExpiryDate());
        // ... any other fields if needed

        if (paymentMethod instanceof VisaPaymentMethod) {
            paymentMethodDTO.setCardType(CardType.VISA);
        } else if (paymentMethod instanceof MasterCardPaymentMethod) {
            paymentMethodDTO.setCardType(CardType.MASTER_CARD);
        }

        return paymentMethodDTO;
    }

    // Convert PaymentMethodDTO to a concrete PaymentMethod entity
    public static PaymentMethod toEntity(PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod paymentMethod;

        if (paymentMethodDTO.getCardType() == CardType.VISA) {
            paymentMethod = new VisaPaymentMethod();
        } else if (paymentMethodDTO.getCardType() == CardType.MASTER_CARD) {
            paymentMethod = new MasterCardPaymentMethod();
        } else {
            throw new IllegalArgumentException("Unsupported Card Type");
        }

        paymentMethod.setMethodId(paymentMethodDTO.getMethodId());
        paymentMethod.setUserId(paymentMethodDTO.getUserId());
        paymentMethod.setCardNumber(paymentMethodDTO.getCardNumber());
        paymentMethod.setExpiryDate(paymentMethodDTO.getExpiryDate());
        // ... any other fields if needed

        return paymentMethod;
    }
}


/*
package com.edu.miu.converters;

import com.edu.miu.domain.PaymentMethod;
import com.edu.miu.dto.PaymentMethodDTO;

public class PaymentMethodConverter {

    public static PaymentMethodDTO toDTO(PaymentMethod paymentMethod) {
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
        paymentMethodDTO.setMethodId(paymentMethod.getMethodId());
        paymentMethodDTO.setUserId(paymentMethod.getUserId());
        paymentMethodDTO.setCardNumber(paymentMethod.getCardNumber());
        paymentMethodDTO.setExpiryDate(paymentMethod.getExpiryDate());
        // ... any other fields
        return paymentMethodDTO;
    }

    public static PaymentMethod toEntity(PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setMethodId(paymentMethodDTO.getMethodId());
        paymentMethod.setUserId(paymentMethodDTO.getUserId());
        paymentMethod.setCardNumber(paymentMethodDTO.getCardNumber());
        paymentMethod.setExpiryDate(paymentMethodDTO.getExpiryDate());
        // ... any other fields
        return paymentMethod;
    }
}
*/
