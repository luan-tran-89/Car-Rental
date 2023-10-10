package com.edu.miu.service;

import java.util.List;

/**
 * @author gasieugru
 */
public interface CarRentalService {

    List<Object> getCurrentReservations(String userId);

    List<Object> getRentalHistory();

}
