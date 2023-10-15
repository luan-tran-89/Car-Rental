package com.edu.miu.consumer;

import com.edu.miu.dto.CarRentalDto;
import com.edu.miu.model.BusinessException;

/**
 * @author gasieugru
 */
public interface CarRentalConsumer {

    void receiveMessageFromCarRental(CarRentalDto carRentalDto) throws BusinessException;

}
