package com.edu.miu.dto;

import com.edu.miu.entity.User;
import com.edu.miu.enums.CardType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author gasieugru
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {

    private Integer cardId;

//    @NotEmpty(message = "The card holder name is required.")
    @Size(min = 2, max = 100, message = "The length of card holder name must be between 2 and 100 characters.")
    private String cardHolderName;

//    @NotEmpty(message = "The card number is required.")
    @Size(min = 10, max = 10, message = "The length of card number must be 10 characters.")
    private String cardNumber;

    @NotEmpty(message = "The expiration date is required.")
    private String expirationDate;

//    @NotEmpty(message = "The cvv is required.")
    @Size(min = 3, max = 3, message = "The length of cvv must be 3 characters.")
    private String cvv;

//    @NotEmpty(message = "The card type is required.")
    private CardType cardType;

//    @NotEmpty(message = "The userId is required.")
    private Integer userId;

}
