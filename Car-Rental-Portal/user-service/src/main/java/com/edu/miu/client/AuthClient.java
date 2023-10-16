package com.edu.miu.client;

import com.edu.miu.model.LoginRequest;
import com.edu.miu.model.LoginResponse;
import com.edu.miu.model.RefreshTokenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author gasieugru
 */
@FeignClient(name = "AUTH-SERVICE")
public interface AuthClient {

//    @GetMapping("/uaa/refreshToken")
//    Object refreshToken(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
//                        @RequestBody RefreshTokenRequest refreshTokenRequest);

    @GetMapping("/uaa")
    Object login(@RequestBody LoginRequest loginRequest);

    @GetMapping("/uaa/refreshToken")
    Object refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest);

}
