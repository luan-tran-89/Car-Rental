package com.edu.miu.controller;

import com.edu.miu.dto.PaymentDTO;
import com.edu.miu.service.PaymentService;
import com.edu.miu.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentMapper paymentMapper;

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        return ResponseEntity.ok(paymentMapper.toDTO(paymentService.createPayment(paymentMapper.toEntity(paymentDTO))));
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Integer paymentId, @Valid @RequestBody PaymentDTO paymentDTO) {
        if (!paymentId.equals(paymentDTO.getPaymentId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(paymentMapper.toDTO(paymentService.updatePayment(paymentMapper.toEntity(paymentDTO))));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDTO> findPaymentById(@PathVariable Integer paymentId) {
        return ResponseEntity.ok(paymentMapper.toDTO(paymentService.findPaymentById(paymentId)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentDTO>> findPaymentsByUserId(@PathVariable Integer userId) {
        List<PaymentDTO> payments = paymentService.findPaymentsByUserId(userId)
                .stream()
                .map(paymentMapper::toDTO)
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

    // You can add other endpoints and exception handlers based on your application requirements.
}
