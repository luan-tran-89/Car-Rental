package rentalservice.controller;

import rentalservice.RentalService;
import rentalservice.domain.Rental;
import rentalservice.domain.PaymentMethod;
import rentalservice.domain.Car;
import rentalservice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping("/reserve")
    public ResponseEntity<Rental> reserveCar(@RequestBody Car car, @RequestBody User user, @RequestParam Date startDate, @RequestParam Date endDate) {
        Rental newRental = rentalService.reserveCar(car, user, startDate, endDate);
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

    @PostMapping
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

    @PostMapping("/{userId}/add-payment-method")
    public ResponseEntity<Boolean> addPaymentMethod(@PathVariable Integer userId, @RequestBody PaymentMethod paymentMethod) {
        boolean added = rentalService.addPaymentMethod(userId, paymentMethod);
        if (added) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
