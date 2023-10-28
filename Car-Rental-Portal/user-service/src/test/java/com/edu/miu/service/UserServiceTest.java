package com.edu.miu.service;

import com.edu.miu.client.RentalClient;
import com.edu.miu.dto.CardDto;
import com.edu.miu.dto.RegisterUserDto;
import com.edu.miu.dto.UserDto;
import com.edu.miu.entity.Card;
import com.edu.miu.entity.User;
import com.edu.miu.enums.CardType;
import com.edu.miu.enums.FrequentRenterType;
import com.edu.miu.enums.Role;
import com.edu.miu.enums.UserStatus;
import com.edu.miu.mapper.UserMapper;
import com.edu.miu.model.BusinessException;
import com.edu.miu.repo.UserRepository;
import com.edu.miu.security.AuthHelper;
import com.edu.miu.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;


/**
 * @author gasieugru
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private RentalClient rentalClient;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AuthHelper authHelper;

    private User manager;

    private User user;

    private RegisterUserDto registerUserDto;

    private UserDto userDto;

    private Card card;

    private CardDto cardDto;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, rentalClient,
                userMapper, modelMapper, bCryptPasswordEncoder, authHelper);

        manager = User.builder()
                .userName("Testing")
                .firstName("Manager")
                .lastName("Test")
                .email("manager@gmail.com")
                .status(UserStatus.ACTIVE)
                .userRole(Role.MANAGER)
                .frequentRenterType(FrequentRenterType.NONE)
                .phone("2061237894")
                .password(bCryptPasswordEncoder.encode("123"))
                .build();

        user = User.builder()
                .userId(2)
                .userName("Testing")
                .firstName("User")
                .lastName("Test")
                .email("abc@gmail.com")
                .status(UserStatus.ACTIVE)
                .userRole(Role.CUSTOMER)
                .frequentRenterType(FrequentRenterType.NONE)
                .phone("2061237894")
                .password(bCryptPasswordEncoder.encode("123"))
                .cards(new ArrayList<>())
                .build();

        registerUserDto = RegisterUserDto.builder()
                .userName("Testing")
                .firstName("User")
                .lastName("Test")
                .email("abc@gmail.com")
                .status(UserStatus.ACTIVE)
                .userRole(Role.CUSTOMER)
                .frequentRenterType(FrequentRenterType.NONE)
                .phone("2061237894")
                .password("123")
                .build();

        userDto = UserDto.builder()
                .userName("Testing")
                .firstName("User")
                .lastName("Test")
                .email("abc@gmail.com")
                .status(UserStatus.ACTIVE)
                .userRole(Role.CUSTOMER)
                .frequentRenterType(FrequentRenterType.NONE)
                .phone("2061237894")
                .password(bCryptPasswordEncoder.encode("123"))
                .cards(new ArrayList<>())
                .build();

        card = Card.builder()
                .user(user)
                .cardHolderName("Testing")
                .cardNumber("4123567890")
                .expirationDate("10/2025")
                .cvv("222")
                .cardType(CardType.VISA)
                .build();

        cardDto = CardDto.builder()
                .userId(user.getUserId())
                .cardHolderName("Testing")
                .cardNumber("4123567890")
                .expirationDate("10/2025")
                .cvv("222")
                .cardType(CardType.VISA)
                .build();
    }

    @DisplayName("JUnit test for getUserByEmail method.")
    @Test
    void getUserByEmail_Success() {
        String email = user.getEmail();

        given(userRepository.findByEmail(email))
                .willReturn(user);

        given(userMapper.toDto(any()))
                .willReturn(userDto);

        try {
            UserDto result = userService.getUserByEmail(email);
            assertThat(result.getEmail()).endsWith(email);
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for getUserByEmail method which throws exception when user is not found.")
    @Test
    void getUserByEmail_ThrowException_NotFound() {
        String email = user.getEmail();

        given(userRepository.findByEmail(email))
                .willReturn(null);

        assertThrows(BusinessException.class, () -> userService.getUserByEmail(email));
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("JUnit test for createUser method.")
    @Test
    void createUser_Success() {
        given(userRepository.save(user))
                .willReturn(user);

        given(userMapper.toEntity((RegisterUserDto) any()))
                .willReturn(user);

        given(userMapper.toDto(any()))
                .willReturn(userDto);

        try {
            UserDto result = userService.createUser(registerUserDto);
            assertThat(result.getEmail()).isEqualTo(user.getEmail());
            assertThat(result.getFirstName()).isEqualTo(user.getFirstName());
            assertThat(result.getLastName()).isEqualTo(user.getLastName());
            verify(userRepository, times(1)).save(any(User.class));
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for createUser method.")
    @Test
    void createUser_ThrowException_WhenEmailExists() {
        given(userRepository.existsByEmail(user.getEmail()))
                .willReturn(true);

        assertThrows(BusinessException.class, () -> userService.createUser(registerUserDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("JUnit test for updateUser method.")
    @Test
    void updateUser_Success() {
        given(authHelper.getUserId())
                .willReturn(1);

        given(userRepository.findByUserId(any()))
                .willReturn(user);

        given(userRepository.save(user))
                .willReturn(user);

        given(bCryptPasswordEncoder.encode(any()))
                .willReturn("1234");

        UserDto updatedUser = UserDto.builder()
                .userName("Updated User").firstName("Updated").lastName("User")
                .email("updated@gmail.com").phone("2068888888")
                .password("1234")
                .build();

        given(userMapper.toDto(any()))
                .willReturn(updatedUser);

        try {
            UserDto result = userService.updateUser(updatedUser);
            assertThat(result.getEmail()).isEqualTo(user.getEmail());
            assertThat(result.getFirstName()).isEqualTo(user.getFirstName());
            assertThat(result.getLastName()).isEqualTo(user.getLastName());
            verify(userRepository, times(1)).save(any(User.class));
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for updateFullUser method by Admin.")
    @Test
    void updateFullUser_WithAdmin_Success() {
        given(authHelper.getRole())
                .willReturn(Role.ADMIN);

        given(userRepository.findByUserId(any()))
                .willReturn(user);

        given(userRepository.save(user))
                .willReturn(user);

        given(bCryptPasswordEncoder.encode(any()))
                .willReturn("1234");

        UserDto updatedUser = UserDto.builder()
                .userName("Updated User").firstName("Updated").lastName("User")
                .email("updated@gmail.com").phone("2068888888")
                .password("1234").userRole(Role.MANAGER)
                .frequentRenterType(FrequentRenterType.GOLD)
                .status(UserStatus.DISABLE)
                .build();

        given(userMapper.toDto(any()))
                .willReturn(updatedUser);

        try {
            UserDto result = userService.updateFullUser(2, updatedUser);
            assertThat(result.getEmail()).isEqualTo(updatedUser.getEmail());
            assertThat(result.getFirstName()).isEqualTo(updatedUser.getFirstName());
            assertThat(result.getLastName()).isEqualTo(updatedUser.getLastName());
            verify(userRepository, times(1)).save(any(User.class));
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for updateFullUser method by Manager.")
    @Test
    void updateFullUser_WithManager_Success() {
        user.setUserRole(Role.MANAGER);

        given(authHelper.getRole())
                .willReturn(Role.MANAGER);

        given(authHelper.getUserId())
                .willReturn(user.getUserId());

        given(userRepository.findByUserId(any()))
                .willReturn(user);

        given(userRepository.save(user))
                .willReturn(user);

        given(bCryptPasswordEncoder.encode(any()))
                .willReturn("1234");

        UserDto updatedUser = UserDto.builder()
                .userName("Updated User").firstName("Updated").lastName("User")
                .email("updated@gmail.com").phone("2068888888")
                .password("1234").userRole(Role.MANAGER)
                .frequentRenterType(FrequentRenterType.GOLD)
                .status(UserStatus.DISABLE)
                .build();

        given(userMapper.toDto(any()))
                .willReturn(updatedUser);

        try {
            UserDto result = userService.updateFullUser(2, updatedUser);
            assertThat(result.getEmail()).isEqualTo(updatedUser.getEmail());
            assertThat(result.getFirstName()).isEqualTo(updatedUser.getFirstName());
            assertThat(result.getLastName()).isEqualTo(updatedUser.getLastName());
            verify(userRepository, times(1)).save(any(User.class));
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for updateFullUser method updating a customer.")
    @Test
    void updateFullUser_UpdateCustomer_Success() {
        user.setUserRole(Role.CUSTOMER);

        given(authHelper.getRole())
                .willReturn(Role.MANAGER);

        given(userRepository.findByUserId(any()))
                .willReturn(user);

        given(userRepository.save(user))
                .willReturn(user);

        given(bCryptPasswordEncoder.encode(any()))
                .willReturn("1234");

        UserDto updatedUser = UserDto.builder()
                .userName("Updated User").firstName("Updated").lastName("User")
                .email("updated@gmail.com").phone("2068888888")
                .password("1234")
                .frequentRenterType(FrequentRenterType.GOLD)
                .status(UserStatus.DISABLE)
                .build();

        given(userMapper.toDto(any()))
                .willReturn(updatedUser);

        try {
            UserDto result = userService.updateFullUser(2, updatedUser);
            assertThat(result.getEmail()).isEqualTo(updatedUser.getEmail());
            assertThat(result.getFirstName()).isEqualTo(updatedUser.getFirstName());
            assertThat(result.getLastName()).isEqualTo(updatedUser.getLastName());
            verify(userRepository, times(1)).save(any(User.class));
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for updateFullUser method failed.")
    @Test
    void updateFullUser_Failed() {
        given(authHelper.getRole())
                .willReturn(Role.MANAGER);

        given(userRepository.findByUserId(any()))
                .willReturn(user);

        UserDto updatedUser = UserDto.builder()
                .userName("Updated User").firstName("Updated").lastName("User")
                .email("updated@gmail.com").phone("2068888888")
                .password("1234").userRole(Role.ADMIN)
                .frequentRenterType(FrequentRenterType.GOLD)
                .status(UserStatus.DISABLE)
                .build();

        assertThrows(BusinessException.class, () -> userService.updateFullUser(2, updatedUser));
        verify(userRepository, times(0)).save(any(User.class));
    }

    @DisplayName("JUnit test for disableCustomer method.")
    @Test
    void disableCustomer_Success() throws BusinessException {
        given(userRepository.findByEmail(user.getEmail()))
                .willReturn(user);

        given(userRepository.save(user))
                .willReturn(user);

        boolean result = userService.disableCustomer(user.getEmail());

        assertThat(result).isEqualTo(true);
        assertThat(user.getStatus()).isEqualTo(UserStatus.DISABLE);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @DisplayName("JUnit test for disableCustomer method which throws exception when user is not found.")
    @Test
    void disableCustomer_ThrowException_NotFound() throws BusinessException {
        given(userRepository.findByEmail(user.getEmail()))
                .willReturn(null);
        assertThrows(BusinessException.class, () -> userService.disableCustomer(user.getEmail()));
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("JUnit test for disableCustomer method which throws exception when user is not a customer.")
    @Test
    void disableCustomer_ThrowException_WhenNotACustomer() {
        given(userRepository.findByEmail(any()))
                .willReturn(manager);
        assertThrows(BusinessException.class, () -> userService.disableCustomer(user.getEmail()));
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("JUnit test for disableCustomer method which throws exception when user is currently renting.")
    @Test
    void disableCustomer_ThrowException_WhenIsRenting() {
        given(userRepository.findByEmail(any()))
                .willReturn(user);
        given(rentalClient.isUserCurrentlyRenting(any()))
                .willReturn(true);
        assertThrows(BusinessException.class, () -> userService.disableCustomer(user.getEmail()));
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("JUnit test for getAllManagers method.")
    @Test
    void getAllManagers_Success() {
        given(userRepository.findByUserRole(any()))
                .willReturn(new ArrayList<>());
        List<UserDto> result = userService.getAllManagers();
        assertThat(result.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getAllManagers method.")
    @Test
    void getAllCustomers_Success() {
        given(userRepository.findByUserRoleIn(any()))
                .willReturn(new ArrayList<>());
        List<UserDto> result = userService.getAllCustomers();
        assertThat(result.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getUserById method.")
    @Test
    void getUserById_Success() {
        int id = user.getUserId();

        given(userRepository.findByUserId(any()))
                .willReturn(user);

        given(userMapper.toDto(any()))
                .willReturn(userDto);

        try {
            UserDto result = userService.getUserById(id);
            assertThat(result.getEmail()).isEqualTo(user.getEmail());
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for getUserById method which throws exception when user is not found.")
    @Test
    void getUserById_ThrowException_NotFound() {
        int id = user.getUserId();

        given(userRepository.findByUserId(id))
                .willReturn(null);

        assertThrows(BusinessException.class, () -> userService.getUserById(id));
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("JUnit test for disableManager method.")
    @Test
    void disableManager_Success() {
        String email = manager.getEmail();

        given(userRepository.findByEmail(any()))
                .willReturn(manager);

        try {
            boolean result = userService.disableManager(email);
            assertThat(result).isTrue();
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for disableManager method which throws exception when user is not manager.")
    @Test
    void disableManager_Failed() {
        String email = user.getEmail();

        given(userRepository.findByEmail(any()))
                .willReturn(user);

        assertThrows(BusinessException.class, () -> userService.disableManager(email));
    }

    @DisplayName("JUnit test for addCard method.")
    @Test
    void addCard_Success() {
        given(userRepository.findByUserId(any()))
                .willReturn(manager);

        given(userRepository.save(any()))
                .willReturn(user);

        given(modelMapper.map(any(), any()))
                .willReturn(card);

        try {
            userService.addCard(user.getUserId(), cardDto);
            verify(userRepository, times(1)).save(any(User.class));
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for addCard method.")
    @Test
    void addCard_Success1() {
        cardDto.setCardType(CardType.MASTER_CARD);
        given(userRepository.findByUserId(any()))
                .willReturn(user);

        given(modelMapper.map(any(), any()))
                .willReturn(card);

        given(userRepository.save(any()))
                .willReturn(user);

        try {
            userService.addCard(user.getUserId(), cardDto);
            verify(userRepository, times(1)).save(any(User.class));
            assertEquals(user.getCards().size(), 1);
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for updateCard method.")
    @Test
    void updateCard_Success() {
        user.getCards().add(card);

        CardDto updateCardDto = CardDto.builder()
                .userId(user.getUserId())
                .cardHolderName("Updating")
                .cardNumber("9876543210")
                .expirationDate("11/2025")
                .cvv("444")
                .cardType(CardType.MASTER_CARD)
                .build();

        given(userRepository.findByUserId(any()))
                .willReturn(user);

        given(userRepository.save(any()))
                .willReturn(user);

        try {
            userService.updateCard(user.getUserId(), updateCardDto);
            verify(userRepository, times(1)).save(any(User.class));
            assertEquals(user.getCards().get(0).getCardNumber(), updateCardDto.getCardNumber());
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for deleteCard method.")
    @Test
    void deleteCard_Success() {
        user.getCards().add(card);

        given(userRepository.findByUserId(any()))
                .willReturn(user);

        given(userRepository.save(any()))
                .willReturn(user);

        try {
            userService.deleteCard(user.getUserId(), card.getCardId());
            verify(userRepository, times(1)).save(any(User.class));
            assertEquals(user.getCards().size(), 0);
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for deleteCard method which throws exception when card is not found.")
    @Test
    void deleteCard_Failed() {
        user.getCards().add(card);

        given(userRepository.findByUserId(any()))
                .willReturn(user);

        assertThrows(BusinessException.class, () -> userService.deleteCard(user.getUserId(), 3));
    }
}

