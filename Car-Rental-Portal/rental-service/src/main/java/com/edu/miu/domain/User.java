package com.edu.miu.domain;

import com.edu.miu.enums.FrequentRenterType;
import lombok.Data;
import com.edu.miu.enums.UserRole;

import javax.persistence.*;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String username;
    private String password;  // this will be hashed for security purposes in the actual implementation
    private String firstname;
    private String lastname;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Enumerated(EnumType.STRING)
    private FrequentRenterType frequentRenterType;

}
