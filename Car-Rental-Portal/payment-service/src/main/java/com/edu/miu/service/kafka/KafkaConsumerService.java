package com.edu.miu.service.kafka;

import com.edu.miu.domain.Payment;
import com.edu.miu.enums.PaymentStatus;
import com.edu.miu.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private PaymentRepository paymentRepository;

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "payment-topic", groupId = "payment-group")
    public void listenPaymentTopic(String message) {
        logger.info("Received Message in group payment-group: {}", message);

        // Mock Logic: Log the transaction.
        // In a real-world scenario, you might parse the message and save detailed logs or notify another microservice.
        logger.info("Logged new payment transaction: {}", message);
    }

    @KafkaListener(topics = "payment-status-topic", groupId = "payment-group")
    public void listenPaymentStatusTopic(String message) {
        logger.info("Received Message about payment status change in group payment-group: {}", message);

        // Mock Logic: Update the payment status in the database.
        // This assumes the message contains a payment ID and its new status.
        // We'd normally use a more structured format (like JSON) for this message.
        String[] parts = message.split(":");
        Integer paymentId = Integer.valueOf(parts[0]);
        String newStatus = parts[1];

        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if(payment != null) {
            payment.setStatus(PaymentStatus.valueOf(newStatus));
            paymentRepository.save(payment);
            logger.info("Updated status for payment ID {}: {}", paymentId, newStatus);
        }

        // Additionally, notify another service about the status change or send an email notification, etc.
    }

    @KafkaListener(topics = "payment-validation-topic", groupId = "payment-group")
    public void listenPaymentValidationTopic(String message) {
        logger.info("Received Payment Validation in group payment-group: {}", message);

        // Mock Logic: Validate the payment based on certain rules.
        // For simplicity, let's assume we're just checking if the payment amount is below a threshold.
        String[] parts = message.split(":");
        Integer paymentId = Integer.valueOf(parts[0]);
        Double paymentAmount = Double.valueOf(parts[1]);

        if(paymentAmount > 5000) {
            logger.warn("Payment with ID {} failed validation due to high amount.", paymentId);
            // Update the payment status in the database to 'FAILED' or similar.
            Payment payment = paymentRepository.findById(paymentId).orElse(null);
            if(payment != null) {
                payment.setStatus(PaymentStatus.FAILED);
                paymentRepository.save(payment);
            }
        }

        // Maybe send a notification or call another service with the validation result.
    }

    // More listeners can be added as needed for other topics.
}



/*package com.edu.miu.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "payment-topic", groupId = "payment-group")
    public void listenPaymentTopic(String message) {
        logger.info("Received Message in group payment-group: {}", message);
        // TODO: Process the payment message.
    }

    @KafkaListener(topics = "payment-status-topic", groupId = "payment-group")
    public void listenPaymentStatusTopic(String message) {
        logger.info("Received Message about payment status change in group payment-group: {}", message);
        // TODO: Process the payment status change message.
    }

    @KafkaListener(topics = "payment-validation-topic", groupId = "payment-group")
    public void listenPaymentValidationTopic(String message) {
        logger.info("Received Payment Validation in group payment-group: {}", message);
        // TODO: Handle payment validations.
    }

    // More listeners can be added as needed for other topics.
}*/



/*
package com.edu.miu.service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "payment-topic", groupId = "payment-group")
    public void listen(String message) {
        // TODO: Process the message
        System.out.println("Received Messasge in group payment-group: " + message);
    }

    @KafkaListener(topics = "payment-validation-topic", groupId = "payment-group")
    public void updatePaymentStatus(String message) {
        // Assume message contains paymentMethodId and its new status separated by a comma
        String[] parts = message.split(",");
        Integer paymentMethodId = Integer.parseInt(parts[0]);
        String status = parts[1];

        // TODO: Implement the logic to fetch the payment method by its ID and update its status
        System.out.println("Received update for Payment Method ID " + paymentMethodId + " with status: " + status);
    }

}
*/
