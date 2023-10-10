package com.edu.miu.service;

import com.edu.miu.model.LoginRequest;
import com.edu.miu.model.LoginResponse;
import com.edu.miu.model.RefreshTokenRequest;

/**
 * @author gasieugru
 */
public interface LoginService {

    /**
     * Call login to get token
     * @param loginRequest
     * @return LoginResponse
     */
    LoginResponse login(LoginRequest loginRequest);

    /**
     * Call refresh token
     * @param refreshTokenRequest
     * @return new token
     */
    LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
