package com.edu.miu.mapper;


import com.edu.miu.dto.CarDto;
import com.edu.miu.dto.MaintenanceDto;
import com.edu.miu.entity.Car;
import com.edu.miu.entity.Maintenance;
import org.springframework.stereotype.Component;

/**
 * @author gasieugru
 */
@Component
public class MaintenanceMapper extends Mapper<Maintenance, MaintenanceDto> {

    public MaintenanceMapper() {
        super(Maintenance.class, MaintenanceDto.class);
    }

    @Override
    public Maintenance toEntity(MaintenanceDto t){
        if (t == null) {
            return null;
        }
        return modelMapper
                .typeMap(MaintenanceDto.class, Maintenance.class)
                .addMappings(m -> m.skip(Maintenance::setCar))
                .map(t);
    }
}
