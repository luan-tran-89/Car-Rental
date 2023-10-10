package com.edu.miu.entity;

import com.edu.miu.enums.Role;
import com.edu.miu.enums.UserStatus;
import lombok.Data;

import javax.persistence.*;

/**
 * @author gasieugru
 */
@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "")
    private UserStatus status;

}
