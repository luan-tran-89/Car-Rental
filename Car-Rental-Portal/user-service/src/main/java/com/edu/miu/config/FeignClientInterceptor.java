package com.edu.miu.config;

import com.edu.miu.security.AuthHelper;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication auth = authHelper.getAuthentication();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
            requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, authHelper.getToken()));
        }
    }

}
