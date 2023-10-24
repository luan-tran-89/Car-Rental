package com.edu.miu.mapper;

import com.edu.miu.dto.CardDto;
import com.edu.miu.dto.RegisterUserDto;
import com.edu.miu.dto.UserDto;
import com.edu.miu.entity.Card;
import com.edu.miu.entity.User;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gasieugru
 */
@Component
public class UserMapper extends Mapper<User, UserDto> {

    @Autowired
    private CardMapper cardMapper;

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
            cardDtos = cardMapper.toListDto(cards);
        }

        UserDto userDto = modelMapper
                .typeMap(User.class, UserDto.class)
                .addMappings(m -> m.skip(UserDto::setCards))
                .addMappings(m -> m.skip(UserDto::setPassword))
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
