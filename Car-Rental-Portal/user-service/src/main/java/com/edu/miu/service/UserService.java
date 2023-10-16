package com.edu.miu.service;

import com.edu.miu.dto.RegisterUserDto;
import com.edu.miu.dto.UserDto;
import com.edu.miu.model.BusinessException;
import com.edu.miu.model.PaymentMethodRequest;

import java.util.List;

/**
 * @author gasieugru
 */
public interface UserService {

    /**
     * Get All Managers
     * @return list of managers
     */
    List<UserDto> getAllManagers();

    /**
     * Get All Customers
     * @return list of Customers
     */
    List<UserDto> getAllCustomers();

    /**
     * Get User By Email
     * @param email
     * @return UserDto
     * @throws BusinessException
     */
    UserDto getUserByEmail(String email) throws BusinessException;

    /**
     * Get User By Id
     *
     * @param id
     * @return UserDto
     * @throws BusinessException
     */
    UserDto getUserById(int id) throws BusinessException;


    /**
     * Create a new user
     * @param userDto
     * @return UserDto
     */
    UserDto createUser(RegisterUserDto userDto) throws BusinessException;

    /**
     * Disable Manager
     * @param email
     * @return true if successful otherwise false
     * @throws BusinessException
     */
    boolean disableManager(String email) throws BusinessException;

    /**
     * Disable Customer
     * @param email
     * @return true if successful otherwise false
     * @throws BusinessException
     */
    boolean disableCustomer(String email) throws BusinessException;

    /**
     * Add Payment Method
     * @param email
     * @param paymentMethodRequest
     * @throws BusinessException
     */
    void addPaymentMethod(String email, PaymentMethodRequest paymentMethodRequest) throws BusinessException;

}
