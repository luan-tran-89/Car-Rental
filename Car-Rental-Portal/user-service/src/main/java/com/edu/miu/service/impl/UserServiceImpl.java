package com.edu.miu.service.impl;

import com.edu.miu.dto.UserDto;
import com.edu.miu.entity.User;
import com.edu.miu.enums.UserStatus;
import com.edu.miu.mapper.UserMapper;
import com.edu.miu.repo.UserRepository;
import com.edu.miu.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author gasieugru
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public boolean disableCustomer(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return false;
        }

        user.setStatus(UserStatus.DISABLE);
        userRepository.save(user);

        return true;
    }
}
