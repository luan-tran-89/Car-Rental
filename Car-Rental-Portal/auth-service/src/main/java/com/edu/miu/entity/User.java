package com.edu.miu.entity;

import com.edu.miu.enums.FrequentRenterType;
import com.edu.miu.enums.Role;
import com.edu.miu.enums.UserStatus;
import lombok.Data;

import javax.persistence.*;

/**
 * @author gasieugru
 */
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "user_name")
    private String userName;

    @Column(unique=true)
    private String email;

    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "")
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequent_renter_type", columnDefinition = "")
    private FrequentRenterType frequentRenterType = FrequentRenterType.NONE;

}
