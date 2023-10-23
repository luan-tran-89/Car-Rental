package com.edu.miu.mapper;

import com.edu.miu.domain.VisaPaymentMethod;
import com.edu.miu.dto.VisaPaymentMethodDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisaPaymentMethodMapper {

    VisaPaymentMethodDTO toDTO(VisaPaymentMethod visaPaymentMethod);

    VisaPaymentMethod toEntity(VisaPaymentMethodDTO visaPaymentMethodDTO);
}
