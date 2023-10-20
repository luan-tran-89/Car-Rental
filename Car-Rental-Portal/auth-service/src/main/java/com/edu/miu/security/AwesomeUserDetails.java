package com.edu.miu.security;

import com.edu.miu.entity.User;
import com.edu.miu.enums.FrequentRenterType;
import com.edu.miu.enums.Role;
import com.edu.miu.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * @author gasieugru
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AwesomeUserDetails implements UserDetails {

    private Integer userId;

    private String userName;

    private String email;

    @JsonIgnore
    private String password;

    private UserStatus status;

    private FrequentRenterType frequentRenterType;

    private Role role;

    public AwesomeUserDetails(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.status = user.getStatus();
        this.frequentRenterType = user.getFrequentRenterType();
        this.role = user.getUserRole();
    }

    public Role getRole() {
        return role;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var simpleGrantedAuthority = new SimpleGrantedAuthority(String.format("ROLE_%s", role.name().toUpperCase(Locale.ROOT)));
        return List.of(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.status == UserStatus.ACTIVE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status == UserStatus.ACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status == UserStatus.ACTIVE;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }
}
