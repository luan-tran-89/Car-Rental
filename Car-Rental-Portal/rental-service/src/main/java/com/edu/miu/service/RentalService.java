package com.edu.miu.service;

import com.edu.miu.domain.Rental;
//import rentalservice.domain.PaymentMethod;
import com.edu.miu.domain.Car;
import com.edu.miu.domain.User;
import com.edu.miu.dto.PaymentMethodDTO;
import com.edu.miu.dto.RentalDto;
import com.edu.miu.dto.ReportFilter;
import com.edu.miu.enums.TimeReport;

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
    List<RentalDto> findAllRentals();

    /**
     * Get Rentals ByCarId
     *
     * @param carId
     * @return list of RentalDto
     */
    List<RentalDto> getRentalsByCarId(Integer carId);

    /**
     * Get Rentals By User Id
     *
     * @param userId
     * @return list of RentalDto
     */
    List<RentalDto> getRentalsByUserId(Integer userId);

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
     * @param carId The ID of the car to be reserved.
     * @param userId The ID of the user making the reservation.
     * @param startDate The start date of the reservation.
     * @param endDate The end date of the reservation.
     * @return Details of the reserved rental.
     */
    Rental reserveCar(Integer carId, Integer userId, Date startDate, Date endDate);

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

    // to check if the customer is an active rental before we disable him
    boolean isUserCurrentlyRenting(Integer userId);

    /**
     * Get Reservations of a user
     * @param userId
     * @return list of reservations
     */
    List<RentalDto> getReservationsByUser(Integer userId);


    /**
     * Adds a payment method for a user.
     *
     * @param userId ID of the user to whom the payment method will be added.
     * @param paymentMethodDetails The details of the payment method to be added.
     * @return True if payment method was added successfully, false otherwise.
     */
    boolean addPaymentMethod(Integer userId, PaymentMethodDTO paymentMethodDetails);

    /**
     * Finalizes a rental. This includes processing payment and potentially other activities
     * like updating car status, sending notifications, etc.
     *
     * @param rentalId ID of the rental to be finalized.
     * @param paymentMethodId ID of the payment method chosen for this rental.
     * @return Details of the finalized rental.
     */
    Rental finalizeRental(Integer rentalId, Integer paymentMethodId);

    /**
     * Allows for direct booking and payment without the need for a prior reservation.
     *
     * @param carId The ID of the car to be rented.
     * @param userId The ID of the user making the rental.
     * @param startDate The start date of the rental.
     * @param endDate The end date of the rental.
     * @param paymentMethodId ID of the payment method chosen for this rental.
     * @return Details of the direct booking.
     */
    Rental directBooking(Integer carId, Integer userId, Date startDate, Date endDate, Integer paymentMethodId);

    /**
     * Cancels a rental reservation.
     *
     * @param rentalId ID of the rental to be canceled.
     * @param userId ID of the user requesting the cancellation.
     * @return True if the cancellation was successful, false otherwise.
     */
    boolean cancelReservation(Integer rentalId, Integer userId);

    List<RentalDto> fetchRentalsByTimeReport(ReportFilter reportFilter);


}

