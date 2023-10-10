package com.edu.miu.dto;

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

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    private Role userRole;

    private UserStatus status;

}
