// In UserClient.java

package com.edu.miu.feign;

import com.edu.miu.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/users/{userId}")
    User getUserById(@PathVariable("userId") Integer userId);
}
