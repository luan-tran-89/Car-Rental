package com.edu.miu.service;

import com.edu.miu.dto.RegisterUserDto;
import com.edu.miu.dto.UserDto;
import com.edu.miu.entity.User;
import com.edu.miu.enums.FrequentRenterType;
import com.edu.miu.enums.Role;
import com.edu.miu.enums.UserStatus;
import com.edu.miu.mapper.UserMapper;
import com.edu.miu.model.BusinessException;
import com.edu.miu.repo.UserRepository;
import com.edu.miu.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



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
    private UserMapper userMapper =  new UserMapper();

    private User user;

    private RegisterUserDto registerUserDto;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, new UserMapper(), bCryptPasswordEncoder);

        user = User.builder()
                .userName("Testing")
                .firstName("User")
                .lastName("Test")
                .email("abc@gmail.com")
                .status(UserStatus.ACTIVE)
                .userRole(Role.CUSTOMER)
                .frequentRentalType(FrequentRenterType.NONE)
                .phone("2061237894")
                .password(bCryptPasswordEncoder.encode("123"))
                .build();

        registerUserDto = RegisterUserDto.builder()
                .userName("Testing")
                .firstName("User")
                .lastName("Test")
                .email("abc@gmail.com")
                .status(UserStatus.ACTIVE)
                .userRole(Role.CUSTOMER)
                .frequentRentalType(FrequentRenterType.NONE)
                .phone("2061237894")
                .password("123")
                .build();
    }

    @DisplayName("JUnit test for getUserByEmail method.")
    @Test
    void getUserByEmail_Success() throws BusinessException {
        String email = user.getEmail();

        given(userRepository.findByEmail(email))
                .willReturn(user);

        UserDto result = userService.getUserByEmail(email);

        assertThat(result.getEmail()).endsWith(email);
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
    void createUser_Success() throws BusinessException {
        given(userRepository.save(user))
                .willReturn(user);

        UserDto result = userService.createUser(registerUserDto);

        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(result.getLastName()).isEqualTo(user.getLastName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @DisplayName("JUnit test for createUser method.")
    @Test
    void createUser_ThrowException_WhenEmailExists() throws BusinessException {
        given(userRepository.existsByEmail(user.getEmail()))
                .willReturn(true);

        assertThrows(BusinessException.class, () -> userService.createUser(registerUserDto));
        verify(userRepository, never()).save(any(User.class));
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
}

