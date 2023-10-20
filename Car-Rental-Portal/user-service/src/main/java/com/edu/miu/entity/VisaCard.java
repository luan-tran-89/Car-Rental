package com.edu.miu.entity;

import com.edu.miu.enums.CardType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author gasieugru
 */
@Entity
@DiscriminatorValue("VISA")
@Data
public class VisaCard extends Card {

}
