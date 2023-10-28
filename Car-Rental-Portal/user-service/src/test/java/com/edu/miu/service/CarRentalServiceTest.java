package com.edu.miu.service;

import com.edu.miu.client.RentalClient;
import com.edu.miu.service.impl.CarRentalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;


import java.util.ArrayList;
import java.util.function.Supplier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author gasieugru
 */
@ExtendWith(MockitoExtension.class)
public class CarRentalServiceTest {

    @InjectMocks
    private CarRentalServiceImpl carRentalService;

    @Mock
    private RentalClient rentalClient;

    @Mock
    private CircuitBreakerFactory breakerFactory;

    private CircuitBreaker circuitBreaker;

    @BeforeEach
    void setUp() {
        circuitBreaker = Mockito.mock(CircuitBreaker.class);
        given(breakerFactory.create(any()))
                .willReturn(circuitBreaker);
        carRentalService = new CarRentalServiceImpl(rentalClient, breakerFactory);
    }

    @DisplayName("JUnit test for getCurrentReservations method.")
    @Test
    void isUserCurrentlyRenting_Success() {
//        given(rentalClient.isUserCurrentlyRenting(any()))
//                .willReturn(true);

        given(circuitBreaker.run(any(), any()))
                .willReturn(true);
        carRentalService.isUserCurrentlyRenting(1);
//        verify(rentalClient, times(1)).isUserCurrentlyRenting(any());
        verify(circuitBreaker, times(1)).run(any(), any());
    }

    @DisplayName("JUnit test for getCurrentReservations method.")
    @Test
    void getCurrentReservations_Success() {
//        given(rentalClient.getReservations(any()))
//                .willReturn(new ArrayList<>());
        given(circuitBreaker.run(any(), any()))
                .willReturn(new ArrayList<>());
        carRentalService.getCurrentReservations(1);
//        verify(rentalClient, times(1)).getReservations(any());
        verify(circuitBreaker, times(1)).run(any(), any());
    }

    @DisplayName("JUnit test for getAllRentalHistory method.")
    @Test
    void getAllRentalHistory_Success() {
//        given(rentalClient.getAllRentals())
//                .willReturn(new ArrayList<>());
        given(circuitBreaker.run(any(), any()))
                .willReturn(new ArrayList<>());
        carRentalService.getAllRentalHistory();
//        verify(rentalClient, times(1)).getAllRentals();
        verify(circuitBreaker, times(1)).run(any(), any());
    }

    @DisplayName("JUnit test for getRentalHistory method.")
    @Test
    void getRentalHistory_Success() {
//        given(rentalClient.getRentalsByUser(any()))
//                .willReturn(new ArrayList<>());
        given(circuitBreaker.run(any(), any()))
                .willReturn(new ArrayList<>());
        carRentalService.getRentalHistory(1);
//        verify(rentalClient, times(1)).getRentalsByUser(any());
        verify(circuitBreaker, times(1)).run(any(), any());
    }
}
