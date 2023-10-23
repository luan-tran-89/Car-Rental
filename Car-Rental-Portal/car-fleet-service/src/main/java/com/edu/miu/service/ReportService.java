package com.edu.miu.service;

import com.edu.miu.enums.ReportFormat;
import com.edu.miu.enums.TimeReport;

import java.util.List;

/**
 * @author gasieugru
 */
public interface ReportService {


    /**
     * Get the report for all cars
     * @param carId
     * @param format
     * @return
     */
    byte[] getCarReport(int carId, ReportFormat format);


    List<Object> getCarRentalReport(TimeReport timeReport);

    byte[] exportCarRentalReport(TimeReport timeReport, ReportFormat format);
}
