package com.edu.miu.dto;

import com.edu.miu.enums.FrequentRenterType;
import com.edu.miu.enums.Role;
import com.edu.miu.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author gasieugru
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {

    @NotEmpty(message = "The user name is required.")
    @Size(min = 2, max = 100, message = "The length of user name must be between 2 and 100 characters.")
    private String userName;

    @NotEmpty(message = "The email address is required.")
    @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    private String email;

    @NotEmpty(message = "The first name is required.")
    @Size(min = 2, max = 100, message = "The length of first name must be between 2 and 100 characters.")
    private String firstName;

    @NotEmpty(message = "The last name is required.")
    @Size(min = 2, max = 100, message = "The length of last name must be between 2 and 100 characters.")
    private String lastName;

    @NotEmpty(message = "The phone is required.")
    @Size(min = 10, max = 10, message = "The length of phone must be between 2 and 100 characters.")
    private String phone;

    private String password;

    private Role userRole;

    private UserStatus status = UserStatus.ACTIVE;

    private FrequentRenterType frequentRenterType = FrequentRenterType.NONE;
}
