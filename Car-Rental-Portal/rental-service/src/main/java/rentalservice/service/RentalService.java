package rentalservice;

import rentalservice.domain.Rental;
import rentalservice.domain.PaymentMethod;
import rentalservice.domain.Car;
import rentalservice.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Date;

/**
 * Service interface for handling rental-related operations.
 */
public interface RentalService {

    /**
     * Fetches all rentals.
     *
     * @return List of all rentals.
     */
    List<Rental> findAllRentals();

    /**
     * Fetches a rental by its ID.
     *
     * @param id ID of the rental to be fetched.
     * @return An Optional containing the found rental, or empty if not found.
     */
    Optional<Rental> findRentalById(Integer id);

    /**
     * Reserves a car for a user for a specified date range.
     *
     * @param car The car to be reserved.
     * @param user The user making the reservation.
     * @param startDate The start date of the reservation.
     * @param endDate The end date of the reservation.
     * @return Details of the reserved rental.
     */
    Rental reserveCar(Car car, User user, Date startDate, Date endDate);

    /**
     * Creates a new rental.
     *
     * @param rental Rental details to be created.
     * @return Created rental.
     */
    Rental createRental(Rental rental);

    /**
     * Updates a given rental.
     *
     * @param rental Rental details to be updated.
     * @return Updated rental.
     */
    Rental updateRental(Rental rental);

    /**
     * Deletes a rental based on its ID.
     *
     * @param id ID of the rental to be deleted.
     */
    void deleteRental(Integer id);

    /**
     * Marks a rental as complete. This can include processing payment and changing car status.
     *
     * @param rentalId ID of the rental to be completed.
     * @return True if completion was successful, false otherwise.
     */
    boolean completeRental(Integer rentalId);

    /**
     * Adds a payment method for a user.
     *
     * @param userId ID of the user to whom the payment method will be added.
     * @param paymentMethod The payment details to be added.
     * @return True if payment method was added successfully, false otherwise.
     */
    boolean addPaymentMethod(Integer userId, PaymentMethod paymentMethod);
}
