package com.edu.miu.domain;

import com.edu.miu.enums.FrequentRenterType;
import com.edu.miu.enums.UserStatus;
import lombok.Data;
import com.edu.miu.enums.UserRole;

import javax.persistence.*;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(unique=true)
    private String username;

    private String password;  // this will be hashed for security purposes in the actual implementation

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", columnDefinition = "ENUM('ADMIN', 'MANAGER', 'CUSTOMER', 'FREQUENT_RENTER') default 'CUSTOMER'")
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('ACTIVE', 'DISABLE', 'DELETE') default 'ACTIVE'")
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequent_renter_type", columnDefinition = "ENUM('NONE', 'BRONZE', 'SILVER', 'GOLD') default 'NONE'")
    private FrequentRenterType frequentRenterType;

}
