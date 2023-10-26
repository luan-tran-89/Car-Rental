package com.edu.miu.controller;

import com.edu.miu.dto.PaymentMethodDTO;
import com.edu.miu.dto.RentalDto;
import com.edu.miu.dto.ReportFilter;
import com.edu.miu.dto.ReservationDTO;
import com.edu.miu.service.RentalService;
import com.edu.miu.domain.Rental;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rentals")
@Tag(name = "Rental Service", description = "Business Rental Service")
@OpenAPIDefinition(servers = { @Server(url = "/rentals")},
        info = @Info(title = "Car Rental System - Rental Service", version = "v1",
                description = "This is a documentation for the Rental Service",
                license = @License(name = "Apache 2.0", url = "http://car-fleet-license.com"),
                contact = @Contact(url = "http://car-fleet.com", name = "Car Fleet", email = "car-fleet@gmail"))
)
public class RentalController {

    @Autowired
    private RentalService rentalService;

    // Method to reserve a car.
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

    // Method to fetch all rentals.
    @GetMapping
    public ResponseEntity<List<RentalDto>> getAllRentals() {
        List<RentalDto> rentals = rentalService.findAllRentals();
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    // Method to fetch all rentals for a particular car.
    @GetMapping("/car/{carId}")
    public ResponseEntity<List<RentalDto>> getRentalsByCar(@PathVariable("carId") Integer carId) {
        List<RentalDto> rentals = rentalService.getRentalsByCarId(carId);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    // Method to fetch a rental by its ID.
    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Integer id) {
        Optional<Rental> rental = rentalService.findRentalById(id);
        return rental.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Method to create a rental.
    @PostMapping("/create")
    public ResponseEntity<Rental> createRental(@RequestBody Rental rental) {
        Rental createdRental = rentalService.createRental(rental);
        return new ResponseEntity<>(createdRental, HttpStatus.CREATED);
    }

    // Method to update a rental.
    @PutMapping("/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable Integer id, @RequestBody Rental rental) {
        Rental updatedRental = rentalService.updateRental(rental);
        return new ResponseEntity<>(updatedRental, HttpStatus.OK);
    }

    // Method to delete a rental by its ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Integer id) {
        rentalService.deleteRental(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Method to complete a rental.
    @PostMapping("/{id}/complete")
    public ResponseEntity<Boolean> completeRental(@PathVariable Integer id) {
        boolean result = rentalService.completeRental(id);
        if (result) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    // Method to check if the user is currently renting.
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

    // Method to add a payment method for a user.
    @PostMapping("/{userId}/add-payment-method")
    public ResponseEntity<Boolean> addPaymentMethod(@PathVariable Integer userId, @RequestBody PaymentMethodDTO paymentMethodDetails) {
        boolean added = rentalService.addPaymentMethod(userId, paymentMethodDetails);
        if (added) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    // Method to cancel a reservation.
    @DeleteMapping("/cancel/{rentalId}/{userId}")
    public ResponseEntity<Boolean> cancelReservation(@PathVariable Integer rentalId, @PathVariable Integer userId) {
        boolean result = rentalService.cancelReservation(rentalId, userId);
        return result ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    // Method to finalize a rental.
    @PutMapping("/finalize/{rentalId}/{paymentMethodId}")
    public ResponseEntity<Rental> finalizeRental(@PathVariable Integer rentalId, @PathVariable Integer paymentMethodId) {
        Rental finalRental = rentalService.finalizeRental(rentalId, paymentMethodId);
        return new ResponseEntity<>(finalRental, HttpStatus.OK);
    }

    // Method to directly book a car.
    @PostMapping("/direct-booking")
    public ResponseEntity<Rental> directBooking(
            @RequestParam("carId") Integer carId,
            @RequestParam("userId") Integer userId,
            @RequestParam("startDate") Date startDate,
            @RequestParam("endDate") Date endDate,
            @RequestParam("paymentMethodId") Integer paymentMethodId) {
        Rental booking = rentalService.directBooking(carId, userId, startDate, endDate, paymentMethodId);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @GetMapping("/rental-history")
    public ResponseEntity<List<RentalDto>> getRentalHistory(ReportFilter reportFilter) {
        List<RentalDto> rentals = rentalService.fetchRentalsByTimeReport(reportFilter);
        return ResponseEntity.ok(rentals);
    }

  /*  @GetMapping("/car/{carId}")
    public ResponseEntity<List<RentalDto>> getRentalsByUser(@PathVariable("carId") Integer carId) {
        List<RentalDto> rentals = rentalService.getRentalsByCarId(carId);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    } */

}
