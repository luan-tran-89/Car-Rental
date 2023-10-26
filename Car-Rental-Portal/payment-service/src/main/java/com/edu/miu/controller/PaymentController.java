package com.edu.miu.controller;

import com.edu.miu.dto.ErrorResponse;
import com.edu.miu.dto.PaymentDTO;
import com.edu.miu.service.PaymentService;
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
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payment Service", description = "Business Payment Service")
@OpenAPIDefinition(servers = { @Server(url = "/payment-service")},
        info = @Info(title = "Car Rental System - Payment Service", version = "v1",
                description = "This is a documentation for the Payment Service",
                license = @License(name = "Apache 2.0", url = "http://car-fleet-license.com"),
                contact = @Contact(url = "http://car-fleet.com", name = "Car Fleet", email = "car-fleet@gmail"))
)
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        try {
            return ResponseEntity.ok(paymentService.createPayment(paymentDTO));
        } catch (Exception e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));

        }
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<?> updatePayment(@PathVariable Integer paymentId, @Valid @RequestBody PaymentDTO paymentDTO) {
        try {
            return ResponseEntity.ok(paymentService.updatePayment(paymentId, paymentDTO));
        } catch (Exception e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));

        }
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDTO> findPaymentById(@PathVariable Integer paymentId) {
        return ResponseEntity.ok(paymentService.findPaymentById(paymentId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentDTO>> findPaymentsByUserId(@PathVariable Integer userId) {
        List<PaymentDTO> payments = paymentService.findPaymentsByUserId(userId)
                .stream()
                .map(PaymentDTO::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(payments);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePaymentById(@PathVariable Integer paymentId) {
        try {
            paymentService.deletePaymentById(paymentId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) { // A general exception for this example, but you'd likely want to be more specific.
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // This can be enhanced to return a detailed error object instead of a simple string.
        String errorMsg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.joining(","));

        return ResponseEntity.badRequest().body(errorMsg);
    }

    @PostMapping("/process")
    public ResponseEntity<String> processPayment(@RequestParam("paymentMethodId") Integer paymentMethodId,
                                                 @RequestParam("amount") Double amount) {
        try {
            boolean isSuccess = paymentService.processPayment(paymentMethodId, amount);

            if (isSuccess) {
                return ResponseEntity.ok("Payment processed successfully.");
            } else {
                return ResponseEntity.status(400).body("Payment failed due to insufficient balance or other reasons.");
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage()); // Payment method not found or other issues.

        } catch (Exception e) {
            // This catches all other exceptions that might occur, such as database errors.
            return ResponseEntity.status(500).body("An internal error occurred while processing the payment.");
        }
    }


    // You can add other endpoints and exception handlers based on your application requirements.
}
