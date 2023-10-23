package com.edu.miu.mapper;

import com.edu.miu.domain.PaymentMethod;
import com.edu.miu.dto.PaymentMethodDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {
    PaymentMethod toEntity(PaymentMethodDTO dto);
    PaymentMethodDTO toDTO(PaymentMethod entity);
}
