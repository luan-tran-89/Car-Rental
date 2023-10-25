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

/*
import lombok.Data;

import com.edu.miu.enums.PaymentMethodType;
import com.edu.miu.enums.PaymentStatus;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;

    @Column(nullable = false)
    private Double amount;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethodType paymentMethodType;

    private String cardDetails; // You might want to handle this more securely in a real application


    // Additional methods, attributes, or annotations can be added as required.
}
*/
