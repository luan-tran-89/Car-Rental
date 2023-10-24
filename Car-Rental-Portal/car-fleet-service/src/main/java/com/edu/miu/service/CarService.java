package com.edu.miu.service;

import com.edu.miu.dto.CarDto;
import com.edu.miu.dto.MaintenanceDto;
import com.edu.miu.enums.CarStatus;
import com.edu.miu.model.BusinessException;
import com.edu.miu.model.CarFilter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author gasieugru
 */
public interface CarService {

    /**
     * Get Car by car id
     * @param carId
     * @return CarDto
     * @throws BusinessException
     */
    CarDto getCarById(int carId) throws BusinessException;

    /**
     * Add a new car
     * @param car
     * @return CarDto
     */
    CarDto addCar(CarDto car);

    /**
     * Add a new car with image
     * @param car
     * @param image
     * @return CarDto
     */
    CarDto addCarWithImg(CarDto car, MultipartFile image);

    /**
     * Add image to a car
     * @param carId
     * @param image
     * @return CarDto
     */
    CarDto addImgToCar(int carId, MultipartFile image) throws BusinessException;

    /**
     * Remove a car
     * @param carId
     * @return true if successful, otherwise false
     * @throws BusinessException
     */
    boolean removeCar(int carId) throws BusinessException;

    /**
     * Update car
     *
     * @param carId
     * @param carDto
     * @return CarDto
     * @throws BusinessException
     */
    CarDto updateCar(int carId, CarDto carDto) throws BusinessException;

    /**
     * Add maintenance to a car
     * @param carId
     * @param maintenanceDto
     * @return CarDto
     * @throws BusinessException
     */
    CarDto addMaintenance(int carId, MaintenanceDto maintenanceDto) throws BusinessException;

    /**
     * Update maintenance to a car
     * @param carId
     * @param maintenanceDto
     * @return CarDto
     * @throws BusinessException
     */
    CarDto updateMaintenance(int carId, MaintenanceDto maintenanceDto) throws BusinessException;

    /**
     * Search cars based on carFilter
     * @param carFilter
     * @return list of filtered cars
     */
    List<CarDto> filterCars(CarFilter carFilter);

    /**
     * Get MaintenanceH History
     * @param carId
     * @return list of MaintenanceH History for a car
     * @throws BusinessException
     */
    List<MaintenanceDto> getMaintenanceHistory(int carId) throws BusinessException;

    /**
     * Update car status
     * @param carId
     * @param status
     * @throws BusinessException
     */
    void updateCarStatus(int carId, CarStatus status) throws BusinessException;

    /**
     * Get all rental history
     * @return list of rental history
     */
    List<Object> getAllRentalHistory();

    /**
     * Get all rental history by userId
     * @return list of rental history
     */
    List<Object> getRentalHistoryByUserId(int userId);

    /**
     * Get all rental history by carId
     * @return list of rental history
     */
    List<Object> getRentalHistoryByCarId(int carId);
}
