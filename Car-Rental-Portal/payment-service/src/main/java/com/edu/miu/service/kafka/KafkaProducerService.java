package com.edu.miu.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        logger.info("Sending message to {}: {}", topic, message);
        kafkaTemplate.send(topic, message);
    }

    public void sendPaymentMessage(String message) {
        logger.info("Sending message to payment-topic: {}", message);
        kafkaTemplate.send("payment-topic", message);
    }

    public void sendPaymentStatusChangeMessage(String message) {
        logger.info("Sending message about payment status change to payment-status-topic: {}", message);
        kafkaTemplate.send("payment-status-topic", message);
    }

    public void sendValidationMessage(String message) {
        logger.info("Sending validation message to payment-validation-topic: {}", message);
        kafkaTemplate.send("payment-validation-topic", message);
    }

    // Additional methods to send messages to other topics can be added as needed.
}




/*
package com.edu.miu.service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
*/
