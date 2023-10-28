package com.edu.miu.service.impl;

import com.edu.miu.client.RentalClient;
import com.edu.miu.dao.CarDao;
import com.edu.miu.dto.CarDto;
import com.edu.miu.dto.MaintenanceDto;
import com.edu.miu.entity.Car;
import com.edu.miu.entity.Maintenance;
import com.edu.miu.enums.CarStatus;
import com.edu.miu.enums.MaintenanceStatus;
import com.edu.miu.mapper.CarMapper;
import com.edu.miu.mapper.MaintenanceMapper;
import com.edu.miu.model.BusinessException;
import com.edu.miu.model.CarFilter;
import com.edu.miu.repo.CarRepository;
import com.edu.miu.service.AwsService;
import com.edu.miu.service.CarService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gasieugru
 */
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private Logger LOGGER = LoggerFactory.getLogger(CarServiceImpl.class);

    private final CarRepository carRepository;

    private final CarDao carDao;

    private final AwsService awsService;

    private final RentalClient rentalClient;

    private final CarMapper carMapper;

    private final MaintenanceMapper maintenanceMapper;

    private final CircuitBreakerFactory breakerFactory;

    private Car findById(int carId) throws BusinessException {
        return carRepository.findById(carId)
                .orElseThrow(() -> new BusinessException(String.format("Car not found", carId)));
    }

    @Override
    public CarDto getCarById(int carId) throws BusinessException {
        return carMapper.toDto(this.findById(carId));
    }

    @Override
    public CarDto addCar(CarDto carDto) {
        Car car = carMapper.toEntity(carDto);
        carRepository.save(car);
        return carMapper.toDto(car);
    }

    @Override
    @Transactional
    public CarDto addCarWithImg(CarDto carDto, MultipartFile image) {
        Car car = carMapper.toEntity(carDto);
        carRepository.save(car);
        this.addImageToCar(car, image);
        return carMapper.toDto(car);
    }

    @Override
    public CarDto addImgToCar(int carId, MultipartFile image) throws BusinessException {
        Car car = this.findById(carId);
        this.addImageToCar(car, image);
        return carMapper.toDto(car);
    }

    private void addImageToCar(Car car, MultipartFile image) {
        if (image != null) {
            String path = String.format("car_%s", car.getCarId());
            String url = awsService.uploadFile(image, path);
            car.setImage(url);
            carRepository.save(car);
        }
    }

    @Override
    public boolean removeCar(int carId) throws BusinessException {
        Car car = this.findById(carId);

        if (car.getStatus() != CarStatus.AVAILABLE) {
            throw new BusinessException(String.format("The car %s is not available.", carId));
        }

        car.setStatus(CarStatus.DISABLED);
        carRepository.save(car);
        return true;
    }

    @Override
    public CarDto updateCar(int carId, CarDto carDto) throws BusinessException {
        Car car = this.findById(carId);

        if (carDto.getModel() != null && !carDto.getModel().equals(car.getModel())) {
            car.setModel(carDto.getModel());
        }

        if (carDto.getMake() != null && !carDto.getMake().equals(car.getMake())) {
            car.setMake(carDto.getMake());
        }

        if (carDto.getStatus() != null && carDto.getStatus() != car.getStatus()) {
            car.setStatus(carDto.getStatus());
        }

        if (carDto.getImage() != null && carDto.getImage() != car.getImage()) {
            car.setImage(carDto.getImage());
        }

        if (carDto.getCostPerDay() != null && carDto.getCostPerDay() != car.getCostPerDay()) {
            car.setCostPerDay(carDto.getCostPerDay());
        }

        if (carDto.getFixedCost() != null && carDto.getFixedCost() != car.getFixedCost()) {
            car.setFixedCost(carDto.getFixedCost());
        }

        carRepository.save(car);

        return carMapper.toDto(car);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    // READ_COMMITTED, prevents dirty reads.
    // The rest of the concurrency side effects could still happen.
    // So uncommitted changes in concurrent transactions have no impact on us,
    // but if a transaction commits its changes, our result could change by re-querying.
    public CarDto addMaintenance(int carId, MaintenanceDto maintenanceDto) throws BusinessException {
        Car car = this.findById(carId);

        if (car.getStatus() != CarStatus.AVAILABLE) {
            throw new BusinessException(String.format("The car %s is not available.", carId));
        }

        car.setStatus(CarStatus.UNDER_MAINTENANCE);

        car.getMaintenances().stream().forEach(m -> {
           if (m.getStatus() != MaintenanceStatus.FINISHED) {
               m.setStatus(MaintenanceStatus.FINISHED);
           }
        });

        Maintenance maintenance = maintenanceMapper.toEntity(maintenanceDto);
        maintenance.setCar(car);
        car.getMaintenances().add(maintenance);

        carRepository.save(car);
        return carMapper.toDto(car);
    }

    @Override
    public CarDto updateMaintenance(int carId, MaintenanceDto maintenanceDto) throws BusinessException {
        Car car = this.findById(carId);

        if (car.getStatus() != CarStatus.UNDER_MAINTENANCE) {
            throw new BusinessException(String.format("The car %s is not under maintenance.", carId));
        }

        Maintenance maintenance = car.getMaintenances().stream()
                .filter(m -> m.getId() == maintenanceDto.getId())
                .findFirst()
                .orElseThrow(() -> new BusinessException(String.format("Maintenance %s is not found", maintenanceDto.getId())));
        this.updateMaintenance(maintenance, maintenanceDto);

        var isMaintenance = car.getMaintenances().stream()
                .filter(m -> m.getStatus() == MaintenanceStatus.IN_PROGRESS)
                .findAny()
                .isPresent();

        if (!isMaintenance) {
            car.setStatus(CarStatus.AVAILABLE);
        }

        carRepository.save(car);
        return carMapper.toDto(car);
    }


    private void updateMaintenance(Maintenance m, MaintenanceDto dto) {
        if (dto.getStartDate() != null &&
                !m.getStartDate().equals(dto.getStartDate())) {
            m.setStartDate(dto.getStartDate());
        }

        if (dto.getEndDate() != null &&
                m.getEndDate() != dto.getEndDate()) {
            m.setEndDate(dto.getEndDate());
        }

        if (StringUtils.isNotBlank(dto.getDescription()) &&
                !m.getDescription().equals(dto.getDescription())) {
            m.setDescription(dto.getDescription());
        }

        if (dto.getStatus() != null &&
                m.getStatus() != dto.getStatus()) {
            m.setStatus(dto.getStatus());
        }
    }

    @Override
    public List<CarDto> filterCars(CarFilter carFilter) {
        return carDao.filterCars(carFilter);
    }

    @Override
    public List<MaintenanceDto> getMaintenanceHistory(int carId) throws BusinessException {
        var car = this.findById(carId);
        return maintenanceMapper.toListDto(car.getMaintenances());
    }

    @Override
    public void updateCarStatus(int carId, CarStatus status) throws BusinessException {
        var car = this.findById(carId);
        car.setStatus(status);
        carRepository.save(car);
    }

    @Override
    public List<Object> getAllRentalHistory() {
        CircuitBreaker circuitBreaker = breakerFactory.create("all-rentals-fetching");
        var data = circuitBreaker.run(() -> rentalClient.getAllRentals(), throwable -> null);
//        List<Object> data = rentalClient.getAllRentals();
        return data == null ? new ArrayList<>() : data;
    }

    @Override
    public List<Object> getRentalHistoryByUserId(int userId) {
        CircuitBreaker circuitBreaker = breakerFactory.create("user-rentals-fetching");
        var data = circuitBreaker.run(() -> rentalClient.getRentalsByUser(userId), throwable -> null);
//        List<Object> data = rentalClient.getRentalsByUser(userId);
        return data == null ? new ArrayList<>() : data;
    }

    @Override
    public List<Object> getRentalHistoryByCarId(int carId) {
        CircuitBreaker circuitBreaker = breakerFactory.create("car-rentals-fetching");
        var data = circuitBreaker.run(() -> rentalClient.getRentalsByCar(carId), throwable -> null);
//        List<Object> data = rentalClient.getRentalsByCar(carId);
        return data == null ? new ArrayList<>() : data;
    }
}
