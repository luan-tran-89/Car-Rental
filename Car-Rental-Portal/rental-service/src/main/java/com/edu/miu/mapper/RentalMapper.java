package com.edu.miu.mapper;

import com.edu.miu.domain.Rental;
import com.edu.miu.dto.RentalDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RentalMapper {
    Rental toEntity(RentalDto dto);
    RentalDto toDTO(Rental entity);
}
