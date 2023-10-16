// In UserClient.java

package com.edu.miu.feign;

import com.edu.miu.domain.User;
import com.edu.miu.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/users/{userId}")
    UserDto getUserById(@PathVariable("userId") Integer userId);
}
