package com.edu.miu.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import com.edu.miu.enums.PaymentStatus;

@Entity
@Table(name = "payments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "method_id", referencedColumnName = "methodId")
    private PaymentMethod paymentMethod;

    @Column(name = "payment_amount")
    private Double paymentAmount;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;
}
