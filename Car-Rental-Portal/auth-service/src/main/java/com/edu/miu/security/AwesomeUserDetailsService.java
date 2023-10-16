package com.edu.miu.security;

import com.edu.miu.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author gasieugru
 */

@Service("userDetailsService")
@Transactional
@RequiredArgsConstructor
public class AwesomeUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s is not found.", email));
        }

        return new AwesomeUserDetails(user);
    }
}
