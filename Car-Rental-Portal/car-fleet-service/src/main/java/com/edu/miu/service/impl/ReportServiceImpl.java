package com.edu.miu.service.impl;

import com.edu.miu.client.RentalClient;
import com.edu.miu.dto.CarDto;
import com.edu.miu.dto.RentalDto;
import com.edu.miu.dto.ReportFilter;
import com.edu.miu.enums.ReportFormat;
import com.edu.miu.model.CarFilter;
import com.edu.miu.service.CarService;
import com.edu.miu.service.ReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

    private final ResourceLoader resourceLoader;

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
    public List<Object> getCarRentalReport(ReportFilter reportFilter) {
        CircuitBreaker circuitBreaker = breakerFactory.create("reservations-fetching");
        var data = circuitBreaker.run(() -> rentalClient.getRentalReport(reportFilter), throwable -> null);
//        List<Object> data = rentalClient.getRentalReport(reportFilter);
        return data == null ? new ArrayList<>() : data;
    }

    @Override
    public byte[] exportCarRentalReport(ReportFilter reportFilter, ReportFormat format) {
        try {
            var rentalReport = this.getCarRentalReport(reportFilter);
            var data = rentalReport.stream()
                    .map(r -> modelMapper.map(r, RentalDto.class)).collect(Collectors.toList());

            Resource resource = resourceLoader.getResource("classpath:reports/CarRentalReport.jrxml");
            InputStream inputStream = resource.getInputStream();

//            File file = ResourceUtils.getFile("classpath:reports/CarRentalReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);


            Map<String, Object> params = new HashMap<>();
            params.put("title", String.format("Car Rental Report - %s", reportFilter.getTimeReport()));

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

            return this.generateReport(jasperReport, params, dataSource, format);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
