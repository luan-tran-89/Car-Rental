package com.edu.miu.mapper;

import com.edu.miu.dto.CardDto;
import com.edu.miu.dto.RegisterUserDto;
import com.edu.miu.dto.UserDto;
import com.edu.miu.entity.Card;
import com.edu.miu.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gasieugru
 */
@Component
public class UserMapper extends Mapper<User, UserDto> {
    public UserMapper() {
        super(User.class, UserDto.class);
    }

    @Override
    public UserDto toDto(User t){
        if (t == null) {
            return null;
        }

        List<Card> cards = t.getCards();
        List<CardDto> cardDtos = null;

        if (!CollectionUtils.isEmpty(cards)) {
            cardDtos = cards.stream()
                    .map(c -> {
                        CardDto cardDto = modelMapper.typeMap(Card.class, CardDto.class)
                                .addMappings(m -> m.skip(CardDto::setUserId))
                                .map(c);
                        cardDto.setUserId(c.getUser().getUserId());
                        cardDto.setCardId(c.getUser().getUserId());
                        cardDto.setCvv("**" + cardDto.getCvv().substring(0, 1));
                        cardDto.setCardNumber("****" + cardDto.getCardNumber().substring(0, 6));
                        return cardDto;
                    })
                    .collect(Collectors.toList());
        }

        UserDto userDto = modelMapper
                .typeMap(User.class, UserDto.class)
                .addMappings(m -> m.skip(UserDto::setCards))
                .map(t);

        userDto.setCards(cardDtos);
        return userDto;
    }

    public User toEntity(RegisterUserDto userDto) {
        return modelMapper
                .typeMap(RegisterUserDto.class, User.class)
                .map(userDto);
    }

}
