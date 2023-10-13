package rentalservice;

import rentalservice.domain.Rental;
import rentalservice.domain.PaymentMethod;
import rentalservice.domain.Car;
import rentalservice.domain.User;
//import rentalservice.domain.FrequentRenterType;
import rentalservice.enums.CarStatus;
import rentalservice.enums.UserRole;
import rentalservice.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Date;
@Service
public class RentalServiceImpl implements rentalservice.RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    // ... other required repositories and services like CarRepository, UserRepository, PaymentService...

    @Override
    public List<Rental> findAllRentals() {
        return rentalRepository.findAll();
    }

    @Override
    public Optional<Rental> findRentalById(Integer id) {
        return rentalRepository.findById(id);
    }

    @Override
    public Rental reserveCar(Car car, User user, Date startDate, Date endDate) {
        car.setStatus(CarStatus.RESERVED);
        // ... logic to update car in its repository...

        Rental rental = new Rental();
        rental.setCar(car);
        rental.setUser(user);
        rental.setStartDate(startDate);
        rental.setEndDate(endDate);

        return rentalRepository.save(rental);
    }

    @Override
    public Rental createRental(Rental rental) {
        // Calculate rental cost
        Car car = rental.getCar();
        double totalCost = car.getFixedCost() + car.getCostPerDay() * daysBetween(rental.getStartDate(), rental.getEndDate());

        User user = rental.getUser();
        if (user.getRole() == UserRole.FREQUENT_RENTER) {
            totalCost = totalCost * (1 - user.getFrequentRenterType().getDiscount());
        }
        // Store total cost in the Rental (if there's an attribute for it) or proceed to payment

        return rentalRepository.save(rental);
    }

    @Override
    public Rental updateRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    @Override
    public void deleteRental(Integer id) {
        rentalRepository.deleteById(id);
    }

    @Override
    public boolean completeRental(Integer rentalId) {
        Optional<Rental> optionalRental = rentalRepository.findById(rentalId);
        if (optionalRental.isPresent()) {
            Rental rental = optionalRental.get();
            // Process payment...
            // If payment is successful:
            rental.getCar().setStatus(CarStatus.PICKED);
            // ... logic to update car's status in its repository...
            return true;
        }
        return false;
    }

    @Override
    public boolean addPaymentMethod(Integer userId, PaymentMethod paymentMethod) {
        // Logic to add & validate payment method
        // Store payment method in DB, verify it if necessary...
        return true;
    }

    private long daysBetween(Date d1, Date d2) {
        return (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
    }
}
