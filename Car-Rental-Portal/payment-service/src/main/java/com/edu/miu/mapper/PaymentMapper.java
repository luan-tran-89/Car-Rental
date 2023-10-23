package com.edu.miu.mapper;

import com.edu.miu.domain.Payment;
import com.edu.miu.dto.PaymentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toEntity(PaymentDTO dto);
    PaymentDTO toDTO(Payment entity);
}
