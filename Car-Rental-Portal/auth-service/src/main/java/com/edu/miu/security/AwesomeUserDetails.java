package com.edu.miu.security;

import com.edu.miu.entity.User;
import com.edu.miu.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * @author gasieugru
 */
public class AwesomeUserDetails implements UserDetails {

    private String email;

    @JsonIgnore
    private String password;

    private Role role;

    public AwesomeUserDetails(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getUserRole();
    }

    public Role getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        var result = roles.stream()
//                .map(r -> new SimpleGrantedAuthority(String.format("ROLE_%s", r.getRole().toUpperCase(Locale.ROOT))))
//                .collect(Collectors.toList());
        var simpleGrantedAuthority = new SimpleGrantedAuthority(String.format("ROLE_%s", role.name().toUpperCase(Locale.ROOT)));
        return List.of(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
