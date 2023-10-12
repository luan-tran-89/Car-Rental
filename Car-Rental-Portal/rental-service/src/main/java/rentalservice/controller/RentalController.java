package rentalservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rentalservice.domain.Rental;
import rentalservice.service.RentalService;

import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping
    public ResponseEntity<List<Rental>> getAllRentals() {
        return new ResponseEntity<>(rentalService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Integer id) {
        return rentalService.findById(id)
                .map(rental -> new ResponseEntity<>(rental, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Rental> createRental(@RequestBody Rental rental) {
        Rental newRental = rentalService.save(rental);
        return new ResponseEntity<>(newRental, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/reserve")
    public ResponseEntity<Rental> reserveCar(@PathVariable Integer id, @RequestBody Rental rentalInfo) {
        return rentalService.reserveCar(id, rentalInfo)
                .map(rental -> new ResponseEntity<>(rental, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Rental> completeRental(@PathVariable Integer id, @RequestBody Rental rentalInfo) {
        return rentalService.completeRental(id, rentalInfo)
                .map(rental -> new ResponseEntity<>(rental, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
