package com.edu.miu.entity;

import com.edu.miu.enums.FrequentRenterType;
import com.edu.miu.enums.Role;
import com.edu.miu.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

/**
 * @author gasieugru
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(name = "user_role", columnDefinition = "ENUM('ADMIN', 'MANAGER', 'CUSTOMER', 'FREQUENT_RENTER') default 'CUSTOMER'")
    private Role userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('ACTIVE', 'DISABLE', 'DELETE') default 'ACTIVE'")
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequent_renter_type", columnDefinition = "ENUM('NONE', 'BRONZE', 'SILVER', 'GOLD') default 'NONE'")
    private FrequentRenterType frequentRenterType;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards;
}
