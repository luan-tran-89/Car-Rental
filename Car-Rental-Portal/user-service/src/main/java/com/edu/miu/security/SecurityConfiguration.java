package com.edu.miu.security;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gasieugru
 */

@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        jsr250Enabled = true,
        securedEnabled = true
)
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;

    public static final List<String> SWAGGER_PATH = new ArrayList<>() {{
        add("/v3/api-docs");
        add("/swagger-ui/**");
        add("/swagger-ui.html");
        add("/swagger-resources/**");
        add("/webjars/**");
        add("/webjars/swagger-ui/**");
    }};

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(this.getPrefixPaths(SWAGGER_PATH, null)).permitAll()
                .antMatchers("/actuator/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/users/list").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/users/register").permitAll()
//                .antMatchers("/users/manager/**").hasAnyRole("ADMIN")
                .antMatchers("/users/disable/**").hasAnyRole("ADMIN", "MANAGER")
//                .antMatchers(HttpMethod.GET, "/users").permitAll()
//                .antMatchers(HttpMethod.GET, "/users/**").permitAll()
//                .anyRequest()
//                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private String[] getPrefixPaths(List<String> paths, String prefix) {
        List<String> result = paths;
        if (StringUtils.isNotBlank(prefix)) {
            result = paths.stream().map(s -> String.format("/%s%s", prefix, s)).toList();
        }
        return result.toArray(new String[0]);
    }
}
