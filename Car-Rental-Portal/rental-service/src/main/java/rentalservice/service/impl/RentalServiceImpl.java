package rentalservice.service.impl;

import rentalservice.domain.*;
import rentalservice.enums.CarStatus;
import rentalservice.enums.UserRole;
import rentalservice.feign.CarFleetClient;
import rentalservice.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rentalservice.service.KafkaProducerService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RentalServiceImpl implements rentalservice.RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private CarFleetClient carFleetClient;

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

        String message = "{\"carId\":" + car.getCarId() + ", \"status\":\"RESERVED\"}";
        kafkaProducerService.sendMessage("carStatusTopic", message);

        Rental rental = new Rental();
        rental.setCar(car);
        rental.setUser(user);
        rental.setStartDate(startDate);
        rental.setEndDate(endDate);
        return rentalRepository.save(rental);
    }

    @Override
    public Rental createRental(Rental rental) {
        Car car = rental.getCar();
        double totalCost = car.getFixedCost() + car.getCostPerDay() * daysBetween(rental.getStartDate(), rental.getEndDate());

        User user = rental.getUser();
        if (user.getRole() == UserRole.FREQUENT_RENTER) {
            totalCost = totalCost * (1 - user.getFrequentRenterType().getDiscount());
        }
        // Here, you might want to set the totalCost to the rental before saving.

        return rentalRepository.save(rental);
    }

    @Override
    public Rental updateRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    @Override
    public void deleteRental(Integer id) {
        Optional<Rental> rental = rentalRepository.findById(id);
        if (rental.isPresent()) {
            Car car = rental.get().getCar();
            car.setStatus(CarStatus.AVAILABLE);

            String message = "{\"carId\":" + car.getCarId() + ", \"status\":\"AVAILABLE\"}";
            kafkaProducerService.sendMessage("carStatusTopic", message);

            rentalRepository.deleteById(id);
        }
    }

    @Override
    public boolean completeRental(Integer rentalId) {
        Optional<Rental> optionalRental = rentalRepository.findById(rentalId);
        if (optionalRental.isPresent()) {
            Rental rental = optionalRental.get();
            Car car = rental.getCar();
            car.setStatus(CarStatus.PICKED);

            String message = "{\"carId\":" + car.getCarId() + ", \"status\":\"PICKED\"}";
            kafkaProducerService.sendMessage("carStatusTopic", message);

            return true;
        }
        return false;
    }

    @Override
    public boolean addPaymentMethod(Integer userId, PaymentMethod paymentMethod) {
        // Your logic to add & validate payment method
        // This might be dependent on how you've designed the system.
        return true;
    }

    private long daysBetween(Date d1, Date d2) {
        return (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
    }
}
