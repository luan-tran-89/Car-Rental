package com.edu.miu.entity;

import com.edu.miu.enums.CardType;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author gasieugru
 */
@Entity
@DiscriminatorValue("MASTER_CARD")
@Data
public class Mastercard extends Card {

}
