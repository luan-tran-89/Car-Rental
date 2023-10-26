package com.edu.miu.controller;

import com.edu.miu.dto.CarDto;
import com.edu.miu.dto.ErrorResponse;
import com.edu.miu.dto.MaintenanceDto;
import com.edu.miu.dto.ReportFilter;
import com.edu.miu.enums.ReportFormat;
import com.edu.miu.enums.TimeReport;
import com.edu.miu.model.BusinessException;
import com.edu.miu.model.CarFilter;
import com.edu.miu.service.CarService;
import com.edu.miu.service.ReportService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author gasieugru
 */
@RestController
@RequestMapping("/car-fleet")
@RequiredArgsConstructor
@Tag(name = "Car Fleet Service", description = "Business Car Fleet Service")
@OpenAPIDefinition(servers = { @Server(url = "/car-Fleet-service")},
        info = @Info(title = "Car Rental System - Car Fleet Service", version = "v1",
                description = "This is a documentation for the Car Fleet Service",
                license = @License(name = "Apache 2.0", url = "http://car-fleet-license.com"),
                contact = @Contact(url = "http://car-fleet.com", name = "Car Fleet", email = "car-fleet@gmail"))
)
public class CarFleetController {
    private Logger LOGGER = LoggerFactory.getLogger(CarFleetController.class);

    private final CarService carService;

    private final ReportService reportService;

    @GetMapping("/car/{id}")
    public ResponseEntity getCarById(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok().body(carService.getCarById(id));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @GetMapping("/car/search")
    public ResponseEntity filterCar(CarFilter carFilter) {
        return ResponseEntity.ok().body(carService.filterCars(carFilter));
    }

    @PostMapping
    public ResponseEntity addCar(@RequestBody CarDto carDto) {
        return ResponseEntity.ok().body(carService.addCar(carDto));
    }

    @PostMapping(value = "/add", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity addCarWithImg(@RequestParam(value = "car") CarDto carDto, @RequestParam(value = "img") MultipartFile img) {
        return ResponseEntity.ok().body(carService.addCarWithImg(carDto, img));
    }

    @PostMapping(value = "/addImage/{carId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity addCarImg(@PathVariable(value = "carId") int carId, @RequestParam(value = "img") MultipartFile img) {
        try {
            return ResponseEntity.ok().body(carService.addImgToCar(carId, img));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity removeCar(@PathVariable("id") int id) {
        try {
            carService.removeCar(id);
            return ResponseEntity.ok()
                    .body(String.format("The car %s is removed %s successfully" , id));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PatchMapping ("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity updateCar(@PathVariable("id") int id, @RequestBody CarDto carDto) {
        try {
            CarDto car = carService.updateCar(id, carDto);
            return ResponseEntity.ok().body(car);
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @GetMapping("/maintenance-history")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity getCarMaintenanceHistory(@RequestParam("carId") int carId) {
        try {
            List<MaintenanceDto> carMaintenanceDto = carService.getMaintenanceHistory(carId);
            return ResponseEntity.ok().body(carMaintenanceDto);
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PostMapping("/maintenance/{carId}")
    public ResponseEntity addCarMaintenance(@PathVariable("carId") int id, @RequestBody MaintenanceDto maintenanceDto) {
        try {
            CarDto carDto = carService.addMaintenance(id, maintenanceDto);
            return ResponseEntity.ok().body(carDto);
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PutMapping("/maintenance/{carId}")
    public ResponseEntity updateCarMaintenance(@PathVariable("carId") int carId, @RequestBody MaintenanceDto maintenanceDto) throws BusinessException {
        try {
            CarDto carDto = carService.updateMaintenance(carId, maintenanceDto);
            return ResponseEntity.ok().body(carDto);
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @GetMapping("/rental-history/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity getRentalHistoryByUserId(@PathVariable("userId") int userId) {
        var rentals = carService.getRentalHistoryByUserId(userId);
        return ResponseEntity.ok().body(rentals);
    }

    @GetMapping("/rental-history/{carId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity getRentalHistoryByCarId(@PathVariable("carId") int carId) {
        var rentals = carService.getRentalHistoryByCarId(carId);
        return ResponseEntity.ok().body(rentals);
    }

    @GetMapping("/rental-history/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity getAllRentalHistory() {
        var rentals = carService.getAllRentalHistory();
        return ResponseEntity.ok().body(rentals);
    }

    @GetMapping("/car-rental-report")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity getCarRentalReport(ReportFilter reportFilter) {
        return ResponseEntity.ok().body(reportService.getCarRentalReport(reportFilter));
    }

    @GetMapping("/export-car-rental-report")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity exportCarRentalReport(ReportFilter reportFilter,
                                                @RequestParam(value = "format", defaultValue = "PDF") ReportFormat format) {
        return ResponseEntity.ok().body(reportService.exportCarRentalReport(reportFilter, format));
    }

}
