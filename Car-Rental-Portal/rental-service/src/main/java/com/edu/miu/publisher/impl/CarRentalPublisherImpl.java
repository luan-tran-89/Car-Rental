package com.edu.miu.publisher.impl;

import com.edu.miu.dto.CarRentalDto;
import com.edu.miu.publisher.CarRentalPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author gasieugru
 */
@Service
public class CarRentalPublisherImpl implements CarRentalPublisher {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendUpdatedCarMessage(CarRentalDto carRentalDto) {
        kafkaTemplate.send("update-car-rental-topic", carRentalDto);
    }
}
