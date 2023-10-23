package com.edu.miu.repository;

import com.edu.miu.domain.Payment;
import com.edu.miu.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDate;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    // Find all payments by a specific user
    List<Payment> findByPaymentMethod_UserId(Integer userId);

    // Find all payments based on their status
    List<Payment> findByStatus(PaymentStatus status);

    // Find all payments made within a specific date range
    List<Payment> findByTransactionDateBetween(LocalDate startDate, LocalDate endDate);

    // You can continue to add more methods as per your business needs.
    // JpaRepository already provides common methods like save, findById, findAll, delete, etc.
}
