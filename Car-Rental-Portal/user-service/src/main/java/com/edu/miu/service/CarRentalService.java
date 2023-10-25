package com.edu.miu.service;

import java.util.List;

/**
 * @author gasieugru
 */
public interface CarRentalService {

    /**
     * Check if User is currently renting
     * @param userId
     * @return true if user is currently renting, otherwise false
     */
    boolean isUserCurrentlyRenting(Integer userId);

    /**
     * Get Current Reservations
     *
     * @param userId
     * @return list of reservations
     */
    List<Object> getCurrentReservations(int userId);

    /**
     * Get All Rental History
     * @return list of rental history for user
     */
    List<Object> getAllRentalHistory();

    /**
     * Get Rental History for user
     * @param userId
     * @return list of rental history for user
     */
    List<Object> getRentalHistory(int userId);

    /**
     * Get Rental History for user
     * @param carId
     * @return list of rental history for user
     */
    List<Object> getRentalHistoryByCar(int carId);

}
