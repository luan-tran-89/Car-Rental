package com.edu.miu.service;

import com.edu.miu.enums.ReportFormat;

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
    byte[] getRentalReportToCar(int carId, ReportFormat format);

}
