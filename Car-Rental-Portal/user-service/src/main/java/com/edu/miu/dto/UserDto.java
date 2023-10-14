package com.edu.miu.dto;

import com.edu.miu.enums.FrequentRenterType;
import com.edu.miu.enums.Role;
import com.edu.miu.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gasieugru
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private int id;

    private String userName;

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private Role userRole;

    private UserStatus status;

    private FrequentRenterType frequentRenterType;

}
