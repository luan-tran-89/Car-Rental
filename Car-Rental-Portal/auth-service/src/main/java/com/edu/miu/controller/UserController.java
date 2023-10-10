package com.edu.miu.controller;

import com.edu.miu.dto.UserDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author gasieugru
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public Object getUser() {
        return "user";
    }


    @PostMapping("/addManager")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createManager(@RequestBody UserDto userDto) {
        return null;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createCustomer(@RequestBody UserDto userDto) {
        return null;
    }
}
