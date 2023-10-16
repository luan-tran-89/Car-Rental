package com.edu.miu.service.impl;

import com.edu.miu.client.RentalClient;
import com.edu.miu.dto.RegisterUserDto;
import com.edu.miu.dto.UserDto;
import com.edu.miu.entity.User;
import com.edu.miu.enums.Role;
import com.edu.miu.enums.UserStatus;
import com.edu.miu.mapper.UserMapper;
import com.edu.miu.model.BusinessException;
import com.edu.miu.model.PaymentMethodRequest;
import com.edu.miu.repo.UserRepository;
import com.edu.miu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author gasieugru
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RentalClient rentalClient;

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
    public void addPaymentMethod(String email, PaymentMethodRequest paymentMethodRequest) throws BusinessException {
        var user = this.findByEmail(email);

    }
}
