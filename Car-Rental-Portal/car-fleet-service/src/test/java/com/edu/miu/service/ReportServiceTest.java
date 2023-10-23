package com.edu.miu.service;

import com.edu.miu.client.RentalClient;
import com.edu.miu.enums.ReportFormat;
import com.edu.miu.enums.TimeReport;
import com.edu.miu.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author gasieugru
 */
@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @InjectMocks
    ReportServiceImpl reportService;

    @Mock
    CarService carService;

    @Mock
    private CircuitBreakerFactory breakerFactory;

    @Mock
    private CircuitBreaker circuitBreaker;

    @BeforeEach
    void setUp() {
    }

    @DisplayName("JUnit test for getCarReport method.")
    @Test
    void getCarReport_Success() {
        given(carService.filterCars(any()))
                .willReturn(new ArrayList<>());

        byte[] result = reportService.getCarReport(1, ReportFormat.PDF);
        assertEquals(result != null, true);
    }

    @DisplayName("JUnit test for getCarRentalReport method.")
    @Test
    void getCarRentalReport_Success() {
        given(breakerFactory.create(any()))
                .willReturn(circuitBreaker);
        given(circuitBreaker.run(any(), any()))
                .willReturn(new ArrayList<>());
        List<Object> result = reportService.getCarRentalReport(TimeReport.ANNUAL);
        verify(circuitBreaker, times(1)).run(any(), any());
        assertEquals(result != null, true);
    }

    @DisplayName("JUnit test for exportCarRentalReport method.")
    @Test
    void exportCarRentalReport_Success() {
        given(breakerFactory.create(any()))
                .willReturn(circuitBreaker);
        given(circuitBreaker.run(any(), any()))
                .willReturn(new ArrayList<>());

        byte[] result = reportService.exportCarRentalReport(TimeReport.ANNUAL, ReportFormat.XML);
        assertEquals(result != null, true);
    }

}
