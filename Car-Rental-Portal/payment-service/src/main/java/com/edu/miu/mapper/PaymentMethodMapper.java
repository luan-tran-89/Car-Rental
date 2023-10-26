package com.edu.miu.mapper;

import com.edu.miu.domain.PaymentMethod;
import com.edu.miu.domain.VisaPaymentMethod;
import com.edu.miu.domain.MasterCardPaymentMethod;
import com.edu.miu.dto.PaymentMethodDTO;
import com.edu.miu.enums.CardType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    default PaymentMethod toEntity(PaymentMethodDTO dto) {
        if (dto.getCardType() == CardType.VISA) {
            VisaPaymentMethod visa = new VisaPaymentMethod();
            visa.setMethodId(dto.getMethodId());
            visa.setUserId(dto.getUserId());
            visa.setCardNumber(dto.getCardNumber());
            visa.setExpiryDate(dto.getExpiryDate());
            // Add other field mappings as needed...
            return visa;
        } else if (dto.getCardType() == CardType.MASTER_CARD) {
            MasterCardPaymentMethod mastercard = new MasterCardPaymentMethod();
            mastercard.setMethodId(dto.getMethodId());
            mastercard.setUserId(dto.getUserId());
            mastercard.setCardNumber(dto.getCardNumber());
            mastercard.setExpiryDate(dto.getExpiryDate());
            // Add other field mappings as needed...
            return mastercard;
        } else {
            throw new IllegalArgumentException("Unsupported Card Type");
        }
    }

    PaymentMethodDTO toDTO(PaymentMethod entity);
}



/*
package com.edu.miu.mapper;

import com.edu.miu.domain.PaymentMethod;
import com.edu.miu.dto.PaymentMethodDTO;
import com.edu.miu.factory.PaymentMethodFactory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PaymentMethodFactory.class)
public interface PaymentMethodMapper {
    PaymentMethod toEntity(PaymentMethodDTO dto);
    PaymentMethodDTO toDTO(PaymentMethod entity);
}

*/


/*
package com.edu.miu.mapper;

import com.edu.miu.domain.PaymentMethod;
import com.edu.miu.domain.VisaPaymentMethod;
import com.edu.miu.domain.MasterCardPaymentMethod;
import com.edu.miu.dto.PaymentMethodDTO;
import com.edu.miu.enums.CardType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    PaymentMethodDTO toDTO(PaymentMethod entity);

    PaymentMethod toEntity(PaymentMethodDTO dto);

    default CardType determineCardType(PaymentMethod entity) {
        if (entity instanceof VisaPaymentMethod) {
            return CardType.VISA;
        } else if (entity instanceof MasterCardPaymentMethod) {
            return CardType.MASTERCARD;
        } else {
            return null;  // You can throw an exception here if needed
        }
    }

    default PaymentMethod toPaymentMethodEntity(PaymentMethodDTO dto) {
        if (dto.getCardType() == CardType.VISA) {
            return new VisaPaymentMethod();
        } else if (dto.getCardType() == CardType.MASTERCARD) {
            return new MasterCardPaymentMethod();
        } else {
            return null;  // You can throw an exception here if needed
        }
    }
}
*/



/*
package com.edu.miu.mapper;

import com.edu.miu.domain.PaymentMethod;
import com.edu.miu.dto.PaymentMethodDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {
    PaymentMethod toEntity(PaymentMethodDTO dto);
    PaymentMethodDTO toDTO(PaymentMethod entity);
}
*/
