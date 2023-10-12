import rentalservice.domain.Rental;

public interface RentalService {
    double calculateRentalCost(int numberOfDays, FrequentRenterType frequentRenterType);
    Rental reserveCar(Rental rental);
    Rental completeRental(Rental rental);
    //... any other methods you'd like to define
}
