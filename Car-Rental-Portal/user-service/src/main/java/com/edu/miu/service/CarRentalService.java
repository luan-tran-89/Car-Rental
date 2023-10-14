package com.edu.miu.service;

import java.util.List;

/**
 * @author gasieugru
 */
public interface CarRentalService {

    /**
     * Get Current Reservations
     *
     * @param email
     * @return list of reservations
     */
    List<Object> getCurrentReservations(String email);

    /**
     * Get Rental History for user
     * @param email
     * @return list of rental history for user
     */
    List<Object> getRentalHistory(String email);

}
