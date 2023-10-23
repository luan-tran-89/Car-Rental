package com.edu.miu.dto;

import com.edu.miu.enums.FrequentRenterType;
import com.edu.miu.enums.Role;
import com.edu.miu.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author gasieugru
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private int userId;

    private String userName;

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    @JsonIgnore
    private String password;

    private Role userRole;

    private UserStatus status;

    private FrequentRenterType frequentRenterType;

    private List<CardDto> cards;
}
