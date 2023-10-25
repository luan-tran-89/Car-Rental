package com.edu.miu.service.impl;

import com.edu.miu.client.RentalClient;
import com.edu.miu.dto.CarDto;
import com.edu.miu.dto.RentalDto;
import com.edu.miu.enums.ReportFormat;
import com.edu.miu.enums.TimeReport;
import com.edu.miu.model.BusinessException;
import com.edu.miu.model.CarFilter;
import com.edu.miu.service.CarService;
import com.edu.miu.service.ReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gasieugru
 */
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final CarService carService;

    private final RentalClient rentalClient;

    private final CircuitBreakerFactory breakerFactory;

    private final ModelMapper modelMapper;

    @Override
    public byte[] getCarReport(int carId, ReportFormat format) {
        try {
            List<CarDto> cars = carService.filterCars(new CarFilter());

            File file = ResourceUtils.getFile("classpath:reports/CarReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            Map<String, Object> params = new HashMap<>();
            params.put("title", "Car Report");

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(cars);

            return this.generateReport(jasperReport, params, dataSource, format);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] generateReport(JasperReport jasperReport, Map<String, Object> parameters, JRBeanCollectionDataSource dataSource, ReportFormat format) {
        JasperPrint jasperPrint = null;
        byte[] reportContent;
        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            switch (format) {
                case PDF -> reportContent = JasperExportManager.exportReportToPdf(jasperPrint);
                case XML -> reportContent = JasperExportManager.exportReportToXml(jasperPrint).getBytes();
                default -> throw new RuntimeException("Unknown report format");
            }
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        return reportContent;
    }

    @Override
    public List<Object> getCarRentalReport(TimeReport timeReport) {
//        CircuitBreaker circuitBreaker = breakerFactory.create("reservations-fetching");
//        var data = circuitBreaker.run(() -> rentalClient.getRentalReport(timeReport), throwable -> null);
        List<Object> data = rentalClient.getRentalReport(timeReport);
        return data == null ? new ArrayList<>() : data;
    }

    @Override
    public byte[] exportCarRentalReport(TimeReport timeReport, ReportFormat format) {
        try {
            var rentalReport = this.getCarRentalReport(timeReport);
            var data = rentalReport.stream()
                    .map(r -> modelMapper.map(r, RentalDto.class)).collect(Collectors.toList());

//            List<RentalDto> rentalReport = new ArrayList<>();
//            Date today = new Date();
//            rentalReport.add(new RentalDto(1, 1, 5, today, today, 1, 200.0));
//            rentalReport.add(new RentalDto(2, 2, 6, today, today, 2, 100.0));
//            rentalReport.add(new RentalDto(3, 3, 7, today, today, 3, 300.0));
//            rentalReport.add(new RentalDto(4, 4, 8, today, today, 4, 400.0));

            File file = ResourceUtils.getFile("classpath:reports/CarRentalReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            Map<String, Object> params = new HashMap<>();
            params.put("title", String.format("Car Rental Report - %s", timeReport));

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

            return this.generateReport(jasperReport, params, dataSource, format);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
}
