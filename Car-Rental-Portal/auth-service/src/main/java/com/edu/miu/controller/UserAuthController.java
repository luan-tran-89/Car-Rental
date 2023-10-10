package com.edu.miu.controller;

import com.edu.miu.model.LoginRequest;
import com.edu.miu.model.RefreshTokenRequest;
import com.edu.miu.service.LoginService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@Tag(name = "User Authentication", description = "Business User Authentication Services")
public class UserAuthController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        var loginResponse = loginService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        var loginResponse = loginService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(loginResponse);
    }

}
