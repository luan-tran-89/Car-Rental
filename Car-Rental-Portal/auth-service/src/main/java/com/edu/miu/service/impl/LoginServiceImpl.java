package com.edu.miu.service.impl;

import com.edu.miu.model.LoginRequest;
import com.edu.miu.model.LoginResponse;
import com.edu.miu.model.RefreshTokenRequest;
import com.edu.miu.security.AuthHelper;
import com.edu.miu.security.AwesomeUserDetails;
import com.edu.miu.security.JwtHelper;
import com.edu.miu.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

/**
 * @author gasieugru
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;

    private final JwtHelper jwtHelper;

    private final AuthHelper authHelper;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        var result = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        AwesomeUserDetails user = (AwesomeUserDetails) result.getPrincipal();
        var accessToken = jwtHelper.generateToken(user);
        var refreshToken = jwtHelper.generateRefreshToken(user);

        return new LoginResponse(accessToken, refreshToken);
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        var refreshToken = refreshTokenRequest.getRefreshToken();
        var isValid = jwtHelper.validateToken(refreshToken);

        if (isValid) {
            AwesomeUserDetails user = authHelper.getUserDetails();
            var accessToken = jwtHelper.generateToken(user);
            var newRefreshToken = jwtHelper.generateRefreshToken(user);
            return new LoginResponse(accessToken, newRefreshToken);
        }

        return new LoginResponse();
    }
}
