package com.edu.miu.mapper;

import com.edu.miu.dto.CarDto;
import com.edu.miu.entity.Car;
import org.springframework.stereotype.Component;

/**
 * @author gasieugru
 */
@Component
public class CarMapper extends Mapper<Car, CarDto> {
    public CarMapper() {
        super(Car.class, CarDto.class);
    }

    @Override
    public CarDto toDto(Car t){
        if (t == null) {
            return null;
        }
        return modelMapper
                .typeMap(Car.class, CarDto.class)
                .map(t);
    }

}
