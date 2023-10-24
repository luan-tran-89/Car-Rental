package com.edu.miu.controller;

import com.edu.miu.client.AuthClient;
import com.edu.miu.dto.*;
import com.edu.miu.enums.Role;
import com.edu.miu.model.BusinessException;
import com.edu.miu.model.LoginRequest;
import com.edu.miu.model.RefreshTokenRequest;
import com.edu.miu.security.AuthHelper;
import com.edu.miu.service.CarRentalService;
import com.edu.miu.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
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
@OpenAPIDefinition(servers = { @Server(url = "/user-service")},
        info = @Info(title = "Car Rental System - User Service", version = "v1",
                description = "This is a documentation for the User Service",
                license = @License(name = "Apache 2.0", url = "http://car-fleet-license.com"),
                contact = @Contact(url = "http://car-fleet.com", name = "Car Fleet", email = "car-fleet@gmail"))
)
public class UserController {

    private Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final CarRentalService carRentalService;

    private final AuthHelper authHelper;

    private final AuthClient authClient;

    @PostMapping("/refreshToken")
    public ResponseEntity refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authClient.refreshToken(refreshTokenRequest));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity getAllCustomers() {
        return ResponseEntity.ok(userService.getAllCustomers());
    }

    @GetMapping("/manager/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity getAllManagers() {
        return ResponseEntity.ok(userService.getAllManagers());
    }

    @PostMapping("/manager/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
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
            userService.createUser(userDto);
            var response = authClient.login(new LoginRequest(userDto.getEmail(), userDto.getPassword()));
            return ResponseEntity.ok().body(response);
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_CUSTOMER', 'FREQUENT_RENTER')")
    public ResponseEntity update(@RequestBody UserDto userDto) {
        try {
            return ResponseEntity.ok().body(userService.updateUser(userDto));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PutMapping("/manager/update/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity update(@PathVariable(name = "userId") int userId, @RequestBody UserDto userDto) {
        try {
            return ResponseEntity.ok().body(userService.updateFullUser(userId, userDto));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_CUSTOMER', 'FREQUENT_RENTER')")
    public ResponseEntity getUserById(@PathVariable(name = "userId") int userId) {
        try {
            boolean isInvalidUser = authHelper.getRole() == Role.CUSTOMER && userId != authHelper.getUserId();

            if (isInvalidUser) {
                return ResponseEntity.ok()
                        .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name()));
            }

            return ResponseEntity.ok().body(userService.getUserById(userId));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_CUSTOMER', 'FREQUENT_RENTER')")
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

    @GetMapping("/rental-history/car/{carId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity getRentalHistoryByCar(@PathVariable(name = "carId") String carId) {
        var rentals = carRentalService.getRentalHistory(authHelper.getUserId());
        return ResponseEntity.ok().body(rentals);
    }

    @GetMapping("/reservations")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'FREQUENT_RENTER')")
    public ResponseEntity getCurrentReservations() {
        var reservations = carRentalService.getCurrentReservations(authHelper.getUserId());
        return ResponseEntity.ok().body(reservations);
    }

    @GetMapping("/rental-history")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'FREQUENT_RENTER')")
    public ResponseEntity getRentalHistory() {
        var rentals = carRentalService.getRentalHistory(authHelper.getUserId());
        return ResponseEntity.ok().body(rentals);
    }

    @PostMapping("/addCard")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'FREQUENT_RENTER')")
    public ResponseEntity addCard(@RequestBody CardDto cardDto) {
        try {
            userService.addCard(authHelper.getUserId(), cardDto);
            return ResponseEntity.ok().body(new MessageResponse("Add card successfully."));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PutMapping("/updateCard/{cardId}")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'FREQUENT_RENTER')")
    public ResponseEntity updateCard(@PathVariable(value = "cardId") Integer cardId, @RequestBody CardDto cardDto) {
        try {
            cardDto.setCardId(cardId);
            userService.updateCard(authHelper.getUserId(), cardDto);
            return ResponseEntity.ok().body(new MessageResponse("Update card successfully."));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/deleteCard/{cardId}")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'FREQUENT_RENTER')")
    public ResponseEntity deleteCard(@PathVariable(value = "cardId") Integer cardId) {
        try {
            userService.deleteCard(authHelper.getUserId(), cardId);
            return ResponseEntity.ok().body(new MessageResponse("Delete card successfully."));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

}