package com.edu.miu.dto;

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

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

}
