package com.edu.miu.controller;

import com.edu.miu.domain.PaymentMethod;
import com.edu.miu.dto.BusinessException;
import com.edu.miu.dto.ErrorResponse;
import com.edu.miu.dto.PaymentMethodDTO;
import com.edu.miu.enums.CardType;
import com.edu.miu.service.PaymentMethodService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payment-methods")
@Tag(name = "Payment Methods Service", description = "Business Payment Methods Service")
@OpenAPIDefinition(servers = { @Server(url = "/payment-methods")},
        info = @Info(title = "Car Rental System - Payment Methods Service", version = "v1",
                description = "This is a documentation for the Payment Methods Service",
                license = @License(name = "Apache 2.0", url = "http://car-fleet-license.com"),
                contact = @Contact(url = "http://car-fleet.com", name = "Car Fleet", email = "car-fleet@gmail"))
)
public class PaymentMethodController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentMethodController.class);

    @Autowired
    private PaymentMethodService paymentMethodService;

    @PostMapping
    public ResponseEntity<?> createPaymentMethod(@RequestBody PaymentMethodDTO paymentMethod) {
        try {
            PaymentMethodDTO savedPaymentMethod = paymentMethodService.createPaymentMethod(paymentMethod);
            logger.info("PaymentMethod with ID: {} created successfully.", savedPaymentMethod.getMethodId());
            return ResponseEntity.ok(savedPaymentMethod);
        } catch (BusinessException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaymentMethod(@PathVariable Integer id, @RequestBody PaymentMethodDTO paymentMethod) {
        try {
            PaymentMethodDTO updatedPaymentMethod = paymentMethodService.updatePaymentMethod(id, paymentMethod);
            logger.info("PaymentMethod with ID: {} updated successfully.", updatedPaymentMethod.getMethodId());
            return ResponseEntity.ok(updatedPaymentMethod);
        } catch (BusinessException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<PaymentMethodDTO>> findByUserId(@PathVariable Integer userId) {
        List<PaymentMethodDTO> paymentMethods = paymentMethodService.findByUserId(userId);
        return ResponseEntity.ok(paymentMethods);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        Optional<PaymentMethodDTO> paymentMethod = paymentMethodService.findById(id);
        return paymentMethod.map(ResponseEntity::ok).orElseGet(() -> {
            logger.warn("PaymentMethod with ID: {} not found.", id);
            return ResponseEntity.notFound().build();
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        paymentMethodService.deleteById(id);
        logger.info("PaymentMethod with ID: {} deleted successfully.", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{cardType}")
    public ResponseEntity<List<PaymentMethodDTO>> findByCardType(@PathVariable CardType cardType) {
        List<PaymentMethodDTO> paymentMethods = paymentMethodService.findByCardType(cardType);
        return ResponseEntity.ok(paymentMethods);
    }

    @GetMapping("/card/{cardNumber}")
    public ResponseEntity<PaymentMethodDTO> findByCardNumber(@PathVariable String cardNumber) {
        Optional<PaymentMethodDTO> paymentMethod = paymentMethodService.findByCardNumber(cardNumber);
        return paymentMethod.map(ResponseEntity::ok).orElseGet(() -> {
            logger.warn("PaymentMethod with card number: {} not found.", cardNumber);
            return ResponseEntity.notFound().build();
        });
    }
}
