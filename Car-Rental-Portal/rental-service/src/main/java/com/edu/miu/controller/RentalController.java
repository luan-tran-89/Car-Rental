package com.edu.miu.controller;

import com.edu.miu.dto.RentalDto;
import com.edu.miu.service.RentalService;
import com.edu.miu.domain.Rental;
import com.edu.miu.dto.ReservationDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rentals")
@Tag(name = "Rental Service", description = "Business Rental Service")
@OpenAPIDefinition(servers = { @Server(url = "/payment-methods")},
        info = @Info(title = "Car Rental System - Rental Service", version = "v1",
                description = "This is a documentation for the Rental Service",
                license = @License(name = "Apache 2.0", url = "http://car-fleet-license.com"),
                contact = @Contact(url = "http://car-fleet.com", name = "Car Fleet", email = "car-fleet@gmail"))
)
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping("/reserve")
    public ResponseEntity<Rental> reserveCar(@RequestBody ReservationDTO reservation) {
        Rental newRental = rentalService.reserveCar(
                reservation.getCarId(),
                reservation.getUserId(),
                reservation.getStartDate(),
                reservation.getEndDate()
        );
        return new ResponseEntity<>(newRental, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RentalDto>> getAllRentals() {
        List<RentalDto> rentals = rentalService.findAllRentals();
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<List<RentalDto>> getRentalsByCar(@PathVariable("carId") Integer carId) {
        List<RentalDto> rentals = rentalService.getRentalsByCarId(carId);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Integer id) {
        Optional<Rental> rental = rentalService.findRentalById(id);
        return rental.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Rental> createRental(@RequestBody Rental rental) {
        Rental createdRental = rentalService.createRental(rental);
        return new ResponseEntity<>(createdRental, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable Integer id, @RequestBody Rental rental) {
        Rental updatedRental = rentalService.updateRental(rental);
        return new ResponseEntity<>(updatedRental, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Integer id) {
        rentalService.deleteRental(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Boolean> completeRental(@PathVariable Integer id) {
        boolean result = rentalService.completeRental(id);
        if (result) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/user/{userId}/active-rentals")
    public ResponseEntity<Boolean> checkUserActiveRentals(@PathVariable Integer userId) {
        boolean isRenting = rentalService.isUserCurrentlyRenting(userId);
        return new ResponseEntity<>(isRenting, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/reservations")
    public ResponseEntity<List<RentalDto>> getReservations(@PathVariable Integer userId) {
        return new ResponseEntity<>(rentalService.getReservationsByUser(userId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RentalDto>> getRentalsByUser(@PathVariable Integer userId) {
        return new ResponseEntity<>(rentalService.getRentalsByUserId(userId), HttpStatus.OK);
    }


    // Uncomment and adapt the below method once you've decided on the structure for PaymentMethod or its ID equivalent.
    /*
    @PostMapping("/{userId}/add-payment-method")
    public ResponseEntity<Boolean> addPaymentMethod(@PathVariable Integer userId, @RequestBody PaymentMethod paymentMethod) {
        boolean added = rentalService.addPaymentMethod(userId, paymentMethod);
        if (added) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
    */
}
