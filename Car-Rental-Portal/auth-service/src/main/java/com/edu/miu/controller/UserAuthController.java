package com.edu.miu.controller;

import com.edu.miu.dto.ErrorResponse;
import com.edu.miu.model.LoginRequest;
import com.edu.miu.model.RefreshTokenRequest;
import com.edu.miu.security.AuthHelper;
import com.edu.miu.service.LoginService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gasieugru
 */

@RestController
@RequestMapping("/uaa")
@RequiredArgsConstructor
@Tag(name = "Auth Service", description = "Business Auth Service")
@OpenAPIDefinition(servers = { @Server(url = "/auth-service")},
        info = @Info(title = "Car Rental System - Auth Service", version = "v1",
                description = "This is a documentation for the Auth Service",
                license = @License(name = "Apache 2.0", url = "http://car-fleet-license.com"),
                contact = @Contact(url = "http://car-fleet.com", name = "Car Fleet", email = "car-fleet@gmail")
        ))
public class UserAuthController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            var loginResponse = loginService.login(loginRequest);
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            var loginResponse = loginService.refreshToken(refreshTokenRequest);
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

}
