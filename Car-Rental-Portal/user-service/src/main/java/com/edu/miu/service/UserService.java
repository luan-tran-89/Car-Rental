package com.edu.miu.service;

import com.edu.miu.dto.UserDto;

/**
 * @author gasieugru
 */
public interface UserService {

    UserDto createUser(UserDto userDto);

    boolean disableCustomer(String email);

}
