package com.edu.miu.service.impl;

import com.edu.miu.client.RentalClient;
import com.edu.miu.dto.CardDto;
import com.edu.miu.dto.RegisterUserDto;
import com.edu.miu.dto.UserDto;
import com.edu.miu.entity.Card;
import com.edu.miu.entity.Mastercard;
import com.edu.miu.entity.User;
import com.edu.miu.entity.VisaCard;
import com.edu.miu.enums.CardType;
import com.edu.miu.enums.Role;
import com.edu.miu.enums.UserStatus;
import com.edu.miu.mapper.UserMapper;
import com.edu.miu.model.BusinessException;
import com.edu.miu.repo.UserRepository;
import com.edu.miu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author gasieugru
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RentalClient rentalClient;

    private final UserMapper userMapper;

    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private User findById(int userId) throws BusinessException {
        return Optional.ofNullable(userRepository.findByUserId(userId))
                .orElseThrow(() -> new BusinessException(String.format("User %s not found", userId)));
    }

    private User findByEmail(String email) throws BusinessException {
        var user = userRepository.findByEmail(email);
        return Optional.ofNullable(user)
                .orElseThrow(() -> new BusinessException(String.format("User %s not found", email)));
    }

    @Override
    public List<UserDto> getAllManagers() {
        return userMapper.toListDto(userRepository.findByUserRole(Role.MANAGER));
    }

    @Override
    public List<UserDto> getAllCustomers() {
        return userMapper.toListDto(userRepository.findByUserRole(Role.CUSTOMER));
    }

    @Override
    public UserDto getUserByEmail(String email) throws BusinessException {
        var user = this.findByEmail(email);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserById(int id) throws BusinessException {
        var user = userRepository.findById(id);
        return userMapper.toDto(user.orElseThrow(() -> new BusinessException(String.format("User %s not found", id))));
    }

    @Override
    public UserDto createUser(RegisterUserDto userDto) throws BusinessException {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new BusinessException(String.format("User %s already exists", userDto.getEmail()));
        }

        User user = userMapper.toEntity(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public boolean disableManager(String email) throws BusinessException {
        User user = this.findByEmail(email);

        if (user.getUserRole() != Role.MANAGER) {
            throw new BusinessException(String.format("You can't disable user %s with role %s", user.getEmail(), user.getUserRole()));
        }
        user.setStatus(UserStatus.DISABLE);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean disableCustomer(String email) throws BusinessException {
        User user = this.findByEmail(email);
        if (user.getUserRole() != Role.CUSTOMER) {
            throw new BusinessException(String.format("You can't disable user %s with role %s", user.getEmail(), user.getUserRole()));
        }

        var isUserCurrentlyRenting = rentalClient.isUserCurrentlyRenting(user.getUserId());

        if (isUserCurrentlyRenting) {
            throw new BusinessException(String.format("You can't disable user %s because he is renting cars", user.getEmail(), user.getUserRole()));
        }

        user.setStatus(UserStatus.DISABLE);
        userRepository.save(user);
        return true;
    }

    @Override
    public void addCard(Integer userId, CardDto cardDto) throws BusinessException {
        User user = this.findById(userId);

        CardType cardType = cardDto.getCardType();
        Card card;

        switch (cardType) {
            case VISA -> card = modelMapper.map(cardDto, VisaCard.class);
            case MASTER_CARD -> card = modelMapper.map(cardDto, Mastercard.class);
            default -> throw new BusinessException(String.format("Unknown card type %s", cardType));
        }

        card.setUser(user);

        List<Card> cards = user.getCards();
        if (CollectionUtils.isEmpty(cards)) {
            cards = new ArrayList<>();
        }
        cards.add(card);
        user.setCards(cards);

        userRepository.save(user);
    }

    @Override
    public void updateCard(Integer userId, CardDto cardDto) throws BusinessException {
        User user = this.findById(userId);

        Card card = user.getCards().stream()
                .filter(c -> c.getCardId() == cardDto.getCardId())
                .findAny().orElseThrow(() -> new BusinessException(String.format("The card %s does not existed", cardDto.getCardId())));

        if (cardDto.getCardHolderName() != null && !cardDto.getCardHolderName().equals(card.getCardHolderName())) {
            card.setCardHolderName(cardDto.getCardHolderName());
        }

        if (cardDto.getCardNumber() != null && !cardDto.getCardNumber().equals(card.getCardNumber())) {
            card.setCardNumber(cardDto.getCardNumber());
        }

        if (cardDto.getExpirationDate() != null && !cardDto.getExpirationDate().equals(card.getExpirationDate())) {
            card.setExpirationDate(cardDto.getExpirationDate());
        }

        if (cardDto.getCvv() != null && !cardDto.getCvv().equals(card.getCvv())) {
            card.setCvv(cardDto.getCvv());
        }

        if (cardDto.getCardType() != null && !cardDto.getCardType().equals(card.getCardType())) {
            card.setCardType(cardDto.getCardType());
        }

        userRepository.save(user);
    }

    @Override
    public void deleteCard(Integer userId, Integer cardId) throws BusinessException {
        User user = this.findById(userId);

        List<Card> cards = user.getCards().stream()
                .filter(c -> c.getCardId() != cardId)
                .collect(Collectors.toList());

        if (cards.size() == user.getCards().size()) {
            throw new BusinessException(String.format("The card %s does not existed", cardId));
        }

        user.getCards().clear();
        user.getCards().addAll(cards);

        userRepository.save(user);
    }
}
