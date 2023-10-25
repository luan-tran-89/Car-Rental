package com.edu.miu.feign;

import com.edu.miu.domain.Payment;
import com.edu.miu.dto.PaymentMethodDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {

    // Payment related endpoints

    @PostMapping("/api/payments")
    Payment createPayment(@RequestBody Payment payment);

    @GetMapping("/api/payments/{id}")
    Payment findPaymentById(@PathVariable("id") Integer id);

    @PutMapping("/api/payments/{id}")
    Payment updatePayment(@PathVariable("id") Integer id, @RequestBody Payment payment);

    @DeleteMapping("/api/payments/{id}")
    void deletePaymentById(@PathVariable("id") Integer id);

    @GetMapping("/api/payments/user/{userId}")
    List<Payment> findPaymentsByUserId(@PathVariable("userId") Integer userId);

    // PaymentMethod related endpoints

    @PostMapping("/api/paymentmethods")
    PaymentMethodDTO addPaymentMethod(@RequestBody PaymentMethodDTO paymentMethodDetails);

    // Since the returned object is a single record, it's beneficial to return a DTO
    // to separate the domain model from the external representation
    @GetMapping("/api/paymentmethods/{id}")
    PaymentMethodDTO getPaymentMethodById(@PathVariable("id") Integer id);

    @PutMapping("/api/paymentmethods/{id}")
    PaymentMethodDTO updatePaymentMethod(@PathVariable("id") Integer id, @RequestBody PaymentMethodDTO paymentMethodDetails);

    @DeleteMapping("/api/paymentmethods/{id}")
    void deletePaymentMethodById(@PathVariable("id") Integer id);

    @GetMapping("/api/paymentmethods/user/{userId}")
    List<PaymentMethodDTO> findPaymentMethodsByUserId(@PathVariable("userId") Integer userId);

    // Check if the payment method has sufficient balance for a payment
    @GetMapping("/api/paymentmethods/{id}/validateBalance/{amount}")
    boolean validatePaymentMethodBalance(@PathVariable("id") Integer paymentMethodId, @PathVariable("amount") Double amount);

    @PostMapping("/api/payments/process")
    boolean processPayment(@RequestParam("paymentMethodId") Integer paymentMethodId, @RequestParam("amount") Double amount);

}
