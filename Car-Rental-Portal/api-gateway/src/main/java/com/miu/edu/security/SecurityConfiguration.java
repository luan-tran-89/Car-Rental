package com.miu.edu.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author gasieugru
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                //ALLOWING REGISTER API FOR DIRECT ACCESS
                .pathMatchers("/swagger-ui.html", "/webjars/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                .pathMatchers("/actuator/**").permitAll()
                .pathMatchers("/**").permitAll()
                //ALL OTHER APIS ARE AUTHENTICATED
                .anyExchange().authenticated()
                .and()
                .csrf()
                .disable();

        return http.build();
    }
}
