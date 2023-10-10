package com.edu.miu.controller;

import com.edu.miu.dto.UserDto;
import com.edu.miu.enums.Role;
import com.edu.miu.service.CarRentalService;
import com.edu.miu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

/**
 * @author gasieugru
 */

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final CarRentalService carRentalService;

    @PostMapping("/manager/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createManager(@RequestBody UserDto userDto) {
        userDto.setUserRole(Role.MANAGER);
        return null;
    }

    @PutMapping("/disable/{email}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity disableCustomer(@PathVariable("email") String email) {
        boolean success = userService.disableCustomer(email);
        return ResponseEntity.ok()
                .body(String.format("The user %s is disabled %s" , email, success ? "successfully" : "failed"));
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public String register(@RequestBody UserDto userDto) {
        userDto.setUserRole(Role.CUSTOMER);
        userService.createUser(userDto);
        return null;
    }

    @GetMapping
    public Object getUser() {
        return "user";
    }

    @GetMapping("/reservations")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public void getCurrentReservations() {

    }

    @GetMapping("/rental-history")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public void getRentalHistory() {

    }

}