package com.edu.miu.controller;

import com.edu.miu.service.RentalService;
import com.edu.miu.domain.Rental;
import com.edu.miu.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rentals")
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
    public ResponseEntity<List<Rental>> getAllRentals() {
        List<Rental> rentals = rentalService.findAllRentals();
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