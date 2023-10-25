package com.edu.miu.mapper;

import com.edu.miu.dto.CardDto;
import com.edu.miu.entity.Card;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gasieugru
 */
@Component
public class CardMapper extends Mapper<Card, CardDto> {

    public CardMapper() {
        super(Card.class, CardDto.class);
    }

    @Override
    public CardDto toDto(Card c) {
        CardDto cardDto = modelMapper.typeMap(Card.class, CardDto.class)
                .addMappings(m -> m.skip(CardDto::setUserId))
                .map(c);
        cardDto.setUserId(c.getUser().getUserId());
        cardDto.setCvv("**" + cardDto.getCvv().substring(0, 1));
        cardDto.setCardNumber("******" + cardDto.getCardNumber().substring(6));
        return cardDto;
    }

    @Override
    public List<CardDto> toListDto(Iterable<Card> list) {
        return ((List<Card>)list).stream().map(this::toDto).collect(Collectors.toList());
    }
}
