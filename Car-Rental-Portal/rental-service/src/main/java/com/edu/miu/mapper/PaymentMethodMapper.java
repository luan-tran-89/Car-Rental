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
