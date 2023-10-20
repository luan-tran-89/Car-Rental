package com.edu.miu.service.impl;

import com.edu.miu.dto.CarDto;
import com.edu.miu.enums.ReportFormat;
import com.edu.miu.model.BusinessException;
import com.edu.miu.model.CarFilter;
import com.edu.miu.service.CarService;
import com.edu.miu.service.ReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gasieugru
 */
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final CarService carService;

    @Override
    public byte[] getRentalReportToCar(int carId, ReportFormat format) {
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
}
