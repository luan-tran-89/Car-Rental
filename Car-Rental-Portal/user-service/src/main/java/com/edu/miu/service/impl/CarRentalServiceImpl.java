package com.edu.miu.service.impl;

import com.edu.miu.service.CarRentalService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gasieugru
 */
@Service
public class CarRentalServiceImpl implements CarRentalService {

    @Override
    public List<Object> getCurrentReservations(String userId) {
        return null;
    }

    @Override
    public List<Object> getRentalHistory() {
        return null;
    }
}
