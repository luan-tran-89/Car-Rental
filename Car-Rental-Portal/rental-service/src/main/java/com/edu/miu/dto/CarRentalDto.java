package com.edu.miu.dto;

import com.edu.miu.enums.CarStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gasieugru
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRentalDto {

    private int carId;

    private CarStatus status;

}
