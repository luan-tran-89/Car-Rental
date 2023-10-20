package com.edu.miu.entity;

import com.edu.miu.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author gasieugru
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "card_type", discriminatorType = DiscriminatorType.STRING)
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardId;

    @Column(name = "card_holder_name")
    private String cardHolderName;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "expiration_date")
    private String expirationDate;

    private String cvv;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", columnDefinition = "ENUM('VISA', 'MASTER_CARD')", insertable = false, updatable = false)
    private CardType cardType;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

}
