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
import com.edu.miu.security.AuthHelper;
import com.edu.miu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private final AuthHelper authHelper;

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
        return userMapper.toListDto(userRepository.findByUserRoleIn(List.of(Role.CUSTOMER, Role.FREQUENT_RENTER)));
    }

    @Override
    public UserDto getUserByEmail(String email) throws BusinessException {
        var user = this.findByEmail(email);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserById(int id) throws BusinessException {
        var user = this.findById(id);
        return userMapper.toDto(Optional.ofNullable(user).orElseThrow(() -> new BusinessException(String.format("User %s not found", id))));
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

    private void updateUser(User user, UserDto userDto) {
        if (StringUtils.isNotBlank(userDto.getUserName()) && !userDto.getUserName().equals(user.getUserName())) {
            user.setUserName(userDto.getUserName());
        }

        if (StringUtils.isNotBlank(userDto.getEmail()) && !userDto.getEmail().equals(user.getEmail())) {
            user.setEmail(userDto.getEmail());
        }

        if (StringUtils.isNotBlank(userDto.getFirstName()) && !userDto.getFirstName().equals(user.getFirstName())) {
            user.setFirstName(userDto.getFirstName());
        }

        if (StringUtils.isNotBlank(userDto.getLastName()) && !userDto.getLastName().equals(user.getLastName())) {
            user.setLastName(userDto.getLastName());
        }

        if (StringUtils.isNotBlank(userDto.getPhone()) && !userDto.getPhone().equals(user.getPhone())) {
            user.setPhone(userDto.getPhone());
        }

        if (StringUtils.isNotBlank(userDto.getPassword()) && !userDto.getPassword().equals(user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto) throws BusinessException {
        User user = this.findById(authHelper.getUserId());

        this.updateUser(user, userDto);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto updateFullUser(int userId, UserDto userDto) throws BusinessException {
        User user = this.findById(userId);

        Role authRole = authHelper.getRole();
        Role newRole = userDto.getUserRole();

        boolean isValidPermission = false;

        if (!Role.isAdmin(newRole)) {
            if (Role.isAdmin(authRole)) {
                isValidPermission = true;
            } else if (Role.isManager(user.getUserRole())) {
                if ((newRole == null || Role.isManager(newRole)) && user.getUserId() == authHelper.getUserId()) {
                    isValidPermission = true;
                }
            } else if (!Role.isAdmin(user.getUserRole())) {
                if (Role.isManager(authRole) && (newRole == null || !Role.isManager(newRole))) {
                    isValidPermission = true;
                }
            }
        }

        if (!isValidPermission) {
            throw new BusinessException(String.format("You don't have permission to update the user %s.", userId));
        }

        this.updateUser(user, userDto);

        if (userDto.getUserRole() != null && userDto.getUserRole() != user.getUserRole()) {
            user.setUserRole(userDto.getUserRole());
        }

        if (userDto.getStatus() != null && userDto.getStatus() != user.getStatus()) {
            user.setStatus(userDto.getStatus());
        }

        if (userDto.getFrequentRenterType() != null && userDto.getFrequentRenterType() != user.getFrequentRenterType()) {
            user.setFrequentRenterType(userDto.getFrequentRenterType());
        }

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public boolean disableManager(String email) throws BusinessException {
        User user = this.findByEmail(email);

        if (!Role.isManager(user.getUserRole())) {
            throw new BusinessException(String.format("You can't disable user %s with role %s", user.getEmail(), user.getUserRole()));
        }
        user.setStatus(UserStatus.DISABLE);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean disableCustomer(String email) throws BusinessException {
        User user = this.findByEmail(email);
        if (!Role.isCustomer(user.getUserRole())) {
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
        if (cards == null) {
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
