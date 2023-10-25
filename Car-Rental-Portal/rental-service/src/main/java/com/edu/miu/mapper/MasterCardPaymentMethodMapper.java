package com.edu.miu.mapper;

import com.edu.miu.domain.MasterCardPaymentMethod;
import com.edu.miu.dto.MasterCardPaymentMethodDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MasterCardPaymentMethodMapper {

    MasterCardPaymentMethodDTO toDTO(MasterCardPaymentMethod masterCardPaymentMethod);

    MasterCardPaymentMethod toEntity(MasterCardPaymentMethodDTO masterCardPaymentMethodDTO);
}
