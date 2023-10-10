package com.edu.miu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gasieugru
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {

    private String refreshToken;

}
