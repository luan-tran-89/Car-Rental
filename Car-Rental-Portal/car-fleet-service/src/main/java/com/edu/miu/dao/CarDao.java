package com.edu.miu.dao;

import com.edu.miu.dto.CarDto;
import com.edu.miu.model.CarFilter;

import java.util.List;

/**
 * @author gasieugru
 */
public interface CarDao {

    /**
     * Filter cars
     * @param carFilter
     * @return list of cars
     */
    List<CarDto> filterCars(CarFilter carFilter);

}
