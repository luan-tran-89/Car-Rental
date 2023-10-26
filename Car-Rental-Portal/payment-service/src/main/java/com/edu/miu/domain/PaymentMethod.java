package com.edu.miu.domain;

import com.edu.miu.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Using Single Table Inheritance
@DiscriminatorColumn(name = "card_type", discriminatorType = DiscriminatorType.STRING)
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer methodId;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "expiry_date", nullable = false)
    private String expiryDate;

    @Column(nullable = false)
    private String cvv;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", columnDefinition = "ENUM('VISA', 'MASTER_CARD')", insertable = false, updatable = false)
    private CardType cardType;

    @Column(name = "approval_amount", nullable = false)
    private Double approvalAmount = 3000.0;

    @Column(name = "used_amount", nullable = false)
    private Double usedAmount = 0.0;

    @Column(name = "total_used", nullable = false)
    private Double totalUsed = 0.0;  // cumulative value of usedAmount

    @Column(name = "balance", nullable = false)
    private Double balance;  // This should be set as approvalAmount - totalUsed

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    // We don't need to specify the card_type here as it will be automatically handled by the DiscriminatorColumn

    // Additional methods or annotations if necessary...
    @PostLoad // This is a JPA callback method which will be invoked after an entity has been loaded into the current persistence context.
    public void updateBalance() {
        this.balance = approvalAmount - totalUsed;
    }
}

