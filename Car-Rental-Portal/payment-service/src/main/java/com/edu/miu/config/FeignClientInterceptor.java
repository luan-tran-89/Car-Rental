package com.edu.miu.config;

import com.edu.miu.security.AuthHelper;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author gasieugru
 */
@Component
@RequiredArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER="Authorization";
    private static final String TOKEN_TYPE = "Bearer";

    private final AuthHelper authHelper;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (authHelper.getAuthentication() != null) {
            requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, authHelper.getToken()));
        }
    }

}
