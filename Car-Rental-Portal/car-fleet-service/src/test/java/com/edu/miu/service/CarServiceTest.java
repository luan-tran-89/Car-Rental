package com.edu.miu.service;

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
import com.edu.miu.service.impl.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author gasieugru
 */
@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarDao carDao;

    @Mock
    private AwsService awsService;

    @Mock
    private CarMapper carMapper;

    @Mock
    private MaintenanceMapper maintenanceMapper;

    @Mock
    private RentalClient rentalClient;

    @Mock
    private CircuitBreakerFactory breakerFactory;

    private CircuitBreaker circuitBreaker;

    private Car car;

    private CarDto carDto;

    private CarDto updatedCarDto;

    private MockMultipartFile file;

    private MaintenanceDto maintenanceDto;

    private Maintenance maintenance;

    @BeforeEach
    void setUp() {
        carService = new CarServiceImpl(carRepository, carDao, awsService, rentalClient, carMapper, maintenanceMapper, breakerFactory);

        car = Car.builder()
                .carId(1).model("X5").make("BMW")
                .image("").status(CarStatus.AVAILABLE)
                .fixedCost(50.0).costPerDay(20.0)
                .maintenances(new ArrayList<>())
                .build();
        carDto = CarDto.builder()
                .carId(1).model("X5").make("BMW")
                .image("").status(CarStatus.AVAILABLE)
                .fixedCost(50.0).costPerDay(20.0)
                .build();

        updatedCarDto = CarDto.builder()
                .carId(1).model("CR-V").make("Honda")
                .image("http://test").status(CarStatus.DISABLED)
                .fixedCost(70.0).costPerDay(30.0)
                .build();

        file = new MockMultipartFile(
                "cx5",
                "cx5.png",
                MediaType.TEXT_PLAIN_VALUE,
                "Mazda cx5".getBytes()
        );

        Date today = new Date();
        long ltime = today.getTime() + 5 *24*60*60*1000;
        Date next5Day = new Date(ltime);

        maintenance = Maintenance.builder()
                .id(1).car(car).description("Daily Maintenance")
                .status(MaintenanceStatus.IN_PROGRESS)
                .startDate(today).endDate(next5Day)
                .build();
        maintenanceDto = MaintenanceDto.builder()
                .id(1).carId(1).description("Daily Maintenance")
                .status(MaintenanceStatus.IN_PROGRESS)
                .startDate(today).endDate(next5Day)
                .build();
    }

    @DisplayName("JUnit test for getCarById method.")
    @Test
    void getCarById_Success() {
        int id = car.getCarId();

        given(carRepository.findById(any()))
                .willReturn(Optional.ofNullable(car));

        given(carMapper.toDto(any()))
                .willReturn(carDto);

        CarDto result = null;
        try {
            result = carService.getCarById(id);
            assertThat(result.getCarId()).isEqualTo(car.getCarId());
            verify(carRepository, times(1)).findById(any(Integer.class));
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for addCar method.")
    @Test
    void addCar_Success() {
        given(carRepository.save(any()))
                .willReturn(car);

        given(carMapper.toDto(any()))
                .willReturn(carDto);

        CarDto result = carService.addCar(carDto);

        assertThat(result.getCarId()).isEqualTo(car.getCarId());
        verify(carRepository, times(1)).save(any());
    }

    @DisplayName("JUnit test for addCarWithImg method.")
    @Test
    void addCarWithImg_Success() {
        given(carRepository.save(any()))
                .willReturn(car);

        given(carMapper.toEntity(any()))
                .willReturn(car);

        given(carMapper.toDto(any()))
                .willReturn(carDto);

        given(awsService.uploadFile(any(), any()))
                .willReturn("http://aws.test");

        CarDto result = carService.addCarWithImg(carDto, file);

        assertThat(result.getCarId()).isEqualTo(car.getCarId());
        verify(carRepository, times(2)).save(any());
        verify(awsService, times(1)).uploadFile(any(), any());
    }

    @DisplayName("JUnit test for addCarWithImg method without Image.")
    @Test
    void addCarWithImg_Success_WithoutImage() {
        given(carRepository.save(any()))
                .willReturn(car);

        given(carMapper.toEntity(any()))
                .willReturn(car);

        given(carMapper.toDto(any()))
                .willReturn(carDto);

        CarDto result = carService.addCarWithImg(carDto, null);

        assertThat(result.getCarId()).isEqualTo(car.getCarId());
        verify(carRepository, times(1)).save(any());
        verify(awsService, times(0)).uploadFile(any(), any());
    }

    @DisplayName("JUnit test for addCarImg method.")
    @Test
    void addCarImg_Success() {
        given(carRepository.findById(any()))
                .willReturn(Optional.ofNullable(car));

        given(carMapper.toDto(any()))
                .willReturn(carDto);

        given(awsService.uploadFile(any(), any()))
                .willReturn("http://aws.test");

        try {
            CarDto result = carService.addImgToCar(car.getCarId(), file);
            assertThat(result.getCarId()).isEqualTo(car.getCarId());
            verify(carRepository, times(1)).save(any());
            verify(awsService, times(1)).uploadFile(any(), any());
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for removeCar method.")
    @Test
    void removeCar_Success() {
        given(carRepository.findById(any()))
                .willReturn(Optional.ofNullable(car));

        given(carRepository.save(any()))
                .willReturn(car);

        try {
            boolean result = carService.removeCar(1);
            assertThat(result).isEqualTo(true);
            verify(carRepository, times(1)).save(any());
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for removeCar method throws exception when the car is not available.")
    @Test
    void removeCar_Failed() {
        car.setStatus(CarStatus.UNDER_MAINTENANCE);
        given(carRepository.findById(any()))
                .willReturn(Optional.ofNullable(car));

        try {
            boolean result = carService.removeCar(1);
            assertThat(result).isEqualTo(true);
            fail("Expected exception should thrown");
        } catch (BusinessException e) {
            verify(carRepository, times(1)).findById(any());
            verify(carRepository, times(0)).save(any());
        }
    }

    @DisplayName("JUnit test for updateCar method.")
    @Test
    void updateCar_Success() {
        given(carRepository.findById(any()))
                .willReturn(Optional.ofNullable(car));

        given(carRepository.save(any()))
                .willReturn(car);

        given(carMapper.toDto(any()))
                .willReturn(updatedCarDto);

        try {
            CarDto result = carService.updateCar(updatedCarDto.getCarId(), updatedCarDto);
            assertThat(result.getStatus()).isEqualTo(updatedCarDto.getStatus());
            verify(carRepository, times(1)).save(any());
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for addMaintenance method.")
    @Test
    void addMaintenance_Success() {
        given(carRepository.save(any()))
                .willReturn(car);

        given(carRepository.findById(any()))
                .willReturn(Optional.ofNullable(car));

        given(maintenanceMapper.toEntity(any()))
                .willReturn(maintenance);

        try {
            CarDto result = carService.addMaintenance(car.getCarId(), maintenanceDto);
            verify(carRepository, times(1)).save(any());
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for addMaintenance method throws exception when the car is not available.")
    @Test
    void addMaintenance_Success_UpdateFinishedMaintenance() {
        maintenance.setStatus(MaintenanceStatus.IN_PROGRESS);
        car.getMaintenances().add(maintenance);

        given(carRepository.findById(any()))
                .willReturn(Optional.ofNullable(car));

        given(carRepository.save(any()))
                .willReturn(car);

        given(maintenanceMapper.toEntity(any()))
                .willReturn(maintenance);

        try {
            carService.addMaintenance(car.getCarId(), maintenanceDto);
            verify(carRepository, times(1)).save(any());
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for addMaintenance method throws exception when the car is not available.")
    @Test
    void addMaintenance_Failed_WhenCarNotAvailable() {
        car.setStatus(CarStatus.RESERVED);

        given(carRepository.findById(any()))
                .willReturn(Optional.ofNullable(car));

        assertThrows(BusinessException.class, () -> carService.addMaintenance(car.getCarId(), maintenanceDto));
        verify(carRepository, times(0)).save(any());
    }

    @DisplayName("JUnit test for updateMaintenance method.")
    @Test
    void updateMaintenance_Success() {
        car.setStatus(CarStatus.UNDER_MAINTENANCE);
        car.getMaintenances().add(maintenance);

        given(carRepository.findById(any()))
                .willReturn(Optional.ofNullable(car));

        given(carRepository.save(any()))
                .willReturn(car);

        try {
            Date today = new Date();
            long ltime = today.getTime() + 8 *24*60*60*1000;
            Date next8Day = new Date(ltime);
            maintenanceDto.setStartDate(today);
            maintenanceDto.setEndDate(next8Day);
            maintenanceDto.setDescription("Update Description");
            carService.updateMaintenance(car.getCarId(), maintenanceDto);
            verify(carRepository, times(1)).save(any());
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for updateMaintenance method for updating available car.")
    @Test
    void updateMaintenance_UpdateAvailableCar_Success() {
        car.setStatus(CarStatus.UNDER_MAINTENANCE);
        car.getMaintenances().add(maintenance);

        given(carRepository.findById(any()))
                .willReturn(Optional.ofNullable(car));

        given(carRepository.save(any()))
                .willReturn(car);

        given(carMapper.toDto(any()))
                .willReturn(carDto);

        try {
            maintenanceDto.setStatus(MaintenanceStatus.FINISHED);
            var carDto = carService.updateMaintenance(car.getCarId(), maintenanceDto);
            verify(carRepository, times(1)).save(any());
            assertThat(carDto.getStatus()).isEqualTo(CarStatus.AVAILABLE);
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for updateMaintenance method.")
    @Test
    void updateMaintenance_Failed_WhenCarNotUnderMaintenance() {
        car.getMaintenances().add(maintenance);

        given(carRepository.findById(any()))
                .willReturn(Optional.ofNullable(car));

        assertThrows(BusinessException.class, () -> carService.updateMaintenance(car.getCarId(), maintenanceDto));
        verify(carRepository, times(0)).save(any());
    }

    @DisplayName("JUnit test for filterCars method.")
    @Test
    void filterCars_Success() {
        given(carDao.filterCars(any()))
                .willReturn(new ArrayList<>());

        carService.filterCars(new CarFilter());
        verify(carDao, times(1)).filterCars(any());
    }

    @DisplayName("JUnit test for getMaintenanceHistory method.")
    @Test
    void getMaintenanceHistory_Success() {
        car.getMaintenances().add(maintenance);
        given(carRepository.findById(any()))
                .willReturn(Optional.ofNullable(car));

        given(maintenanceMapper.toListDto(any()))
                .willReturn(List.of(maintenanceDto));

        try {
            List<MaintenanceDto> maintenanceDtoList = carService.getMaintenanceHistory(carDto.getCarId());
            assertEquals(1, maintenanceDtoList.size());
            verify(carRepository, times(1)).findById(any());
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for updateCarStatus method.")
    @Test
    void updateCarStatus_Success() {
        given(carRepository.findById(any()))
                .willReturn(Optional.ofNullable(car));

        given(carRepository.save(any()))
                .willReturn(car);

        try {
            carService.updateCarStatus(carDto.getCarId(), CarStatus.PICKED);
            verify(carRepository, times(1)).save(any());
            assertEquals(CarStatus.PICKED, car.getStatus());
        } catch (BusinessException e) {
            fail("Expected exception should not thrown");
        }
    }

    @DisplayName("JUnit test for getAllRentalHistory method.")
    @Test
    void getAllRentalHistory_Success() {
        circuitBreaker = Mockito.mock(CircuitBreaker.class);
        given(breakerFactory.create(any()))
                .willReturn(circuitBreaker);
//        given(circuitBreaker.run(any(), any()))
//                .willReturn(new ArrayList<>());

//        given(rentalClient.getAllRentals())
//                .willReturn(new ArrayList<>());
        carService.getAllRentalHistory();
//        verify(rentalClient, times(1)).getAllRentals();
        verify(circuitBreaker, times(1)).run(any(), any());
    }

    @DisplayName("JUnit test for getRentalHistoryByUserId method.")
    @Test
    void getRentalHistoryByUserId_Success() {
        circuitBreaker = Mockito.mock(CircuitBreaker.class);
        given(breakerFactory.create(any()))
                .willReturn(circuitBreaker);
        given(circuitBreaker.run(any(), any()))
                .willReturn(new ArrayList<>());

//        given(rentalClient.getRentalsByUser(any()))
//                .willReturn(new ArrayList<>());
        carService.getRentalHistoryByUserId(1);
//        verify(rentalClient, times(1)).getRentalsByUser(any());
        verify(circuitBreaker, times(1)).run(any(), any());
    }

    @DisplayName("JUnit test for getRentalHistoryByCarId method.")
    @Test
    void getRentalHistoryByCarId_Success() {
        circuitBreaker = Mockito.mock(CircuitBreaker.class);
        given(breakerFactory.create(any()))
                .willReturn(circuitBreaker);
        given(circuitBreaker.run(any(), any()))
                .willReturn(new ArrayList<>());

//        given(rentalClient.getRentalsByCar(any()))
//                .willReturn(new ArrayList<>());
        carService.getRentalHistoryByCarId(1);
//        verify(rentalClient, times(1)).getRentalsByCar(any());
        verify(circuitBreaker, times(1)).run(any(), any());
    }
}
