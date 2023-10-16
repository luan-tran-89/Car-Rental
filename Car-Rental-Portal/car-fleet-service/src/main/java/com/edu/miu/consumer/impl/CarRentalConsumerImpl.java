package com.edu.miu.consumer.impl;

import com.edu.miu.consumer.CarRentalConsumer;
import com.edu.miu.dto.CarRentalDto;
import com.edu.miu.model.BusinessException;
import com.edu.miu.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author gasieugru
 */
@Service
@RequiredArgsConstructor
public class CarRentalConsumerImpl implements CarRentalConsumer {

    private final CarService carService;

    @KafkaListener(
            topics = "update-car-rental-topic",
            containerFactory = "carRentalKafkaListenerContainerFactory",
            groupId = "car-rental-topic")
    @Override
    public void receiveMessageFromCarRental(CarRentalDto carRentalDto) throws BusinessException {
        carService.updateCarStatus(carRentalDto.getCarId(), carRentalDto.getStatus());
    }

}
