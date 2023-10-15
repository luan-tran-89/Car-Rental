package com.edu.miu.controller;

import com.edu.miu.dto.ErrorResponse;
import com.edu.miu.dto.RegisterUserDto;
import com.edu.miu.dto.UserDto;
import com.edu.miu.enums.Role;
import com.edu.miu.model.BusinessException;
import com.edu.miu.model.PaymentMethodRequest;
import com.edu.miu.security.AuthHelper;
import com.edu.miu.service.CarRentalService;
import com.edu.miu.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * @author gasieugru
 */

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Service", description = "Business User Service")
@OpenAPIDefinition(servers = { @Server(url = "User-service")},
        info = @Info(title = "Car Rental System - User Service", version = "v1",
                description = "This is a documentation for the User Service"))
public class UserController {

    private Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final CarRentalService carRentalService;

    private final AuthHelper authHelper;

    @PostMapping("/manager/add")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity createManager(@RequestBody RegisterUserDto userDto) {
        try {
            userDto.setUserRole(Role.MANAGER);
            UserDto user = userService.createUser(userDto);
            return ResponseEntity.ok().body(user);
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PutMapping("/manager/disable/{email}")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity disableManager(@PathVariable("email") String email) {
        try {
            boolean success = userService.disableManager(email);
            return ResponseEntity.ok()
                    .body(String.format("The manager %s is disabled %s" , email, success ? "successfully" : "failed"));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PutMapping("/disable/{email}")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity disableCustomer(@PathVariable("email") String email) {
        try {
            boolean success = userService.disableCustomer(email);
            return ResponseEntity.ok()
                    .body(String.format("The user %s is disabled %s" , email, success ? "successfully" : "failed"));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterUserDto userDto) {
        try {
            userDto.setUserRole(Role.CUSTOMER);
            var user = userService.createUser(userDto);
            return ResponseEntity.ok().body(user);
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity getUser(@RequestParam(name = "email", defaultValue = "") String email) {
        try {
            boolean isNotSameEmail = !email.equals(authHelper.getEmail());
            if (Role.CUSTOMER == authHelper.getRole() && isNotSameEmail) {
                return ResponseEntity.ok()
                        .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name()));
            }

            UserDto userDto = userService.getUserByEmail(email);

            if (isNotSameEmail && authHelper.getRole() == Role.MANAGER &&
                    (userDto.getUserRole() == Role.MANAGER || userDto.getUserRole() == Role.ADMIN)
            ) {
                return ResponseEntity.ok()
                        .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name()));
            }

            return ResponseEntity.ok().body(userService.getUserByEmail(email));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @GetMapping("/reservations")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity getCurrentReservations(@RequestParam(name = "email", defaultValue = "") String email) {
        var reservations = carRentalService.getCurrentReservations(email);
        return ResponseEntity.ok().body(reservations);
    }

    @GetMapping("/rental-history")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity getRentalHistory() {
        var rentals = carRentalService.getRentalHistory(authHelper.getEmail());
        return ResponseEntity.ok().body(rentals);
    }

    @PutMapping("/addPayment/{email}")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_FREQUENT_CUSTOMER')")
    public ResponseEntity addPayment(@PathVariable("email") String email, @RequestBody PaymentMethodRequest paymentMethodRequest) {
        return ResponseEntity.ok("");
    }

}