package com.edu.miu.publisher;

import com.edu.miu.dto.CarRentalDto;

/**
 * @author gasieugru
 */
public interface CarRentalPublisher {

    void sendUpdatedCarMessage(CarRentalDto carRentalDto);

}
