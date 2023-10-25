package com.edu.miu.converters;

import com.edu.miu.domain.Rental;
import com.edu.miu.dto.RentalDto;

public class RentalConverter {

    // Convert Rental entity to RentalDto
    public static RentalDto toDTO(Rental rental) {
        RentalDto rentalDto = new RentalDto();

        rentalDto.setRentalId(rental.getRentalId());
        rentalDto.setCarId(rental.getCarId());
        rentalDto.setUserId(rental.getUserId());
        rentalDto.setStartDate(rental.getStartDate());
        rentalDto.setEndDate(rental.getEndDate());
        rentalDto.setPaymentId(rental.getPaymentId());
        rentalDto.setTotalCost(rental.getTotalCost());

        return rentalDto;
    }

    // Convert RentalDto to Rental entity
    public static Rental toEntity(RentalDto rentalDto) {
        Rental rental = new Rental();

        rental.setRentalId(rentalDto.getRentalId());
        rental.setCarId(rentalDto.getCarId());
        rental.setUserId(rentalDto.getUserId());
        rental.setStartDate(rentalDto.getStartDate());
        rental.setEndDate(rentalDto.getEndDate());
        rental.setPaymentId(rentalDto.getPaymentId());
        rental.setTotalCost(rentalDto.getTotalCost());

        return rental;
    }
}
