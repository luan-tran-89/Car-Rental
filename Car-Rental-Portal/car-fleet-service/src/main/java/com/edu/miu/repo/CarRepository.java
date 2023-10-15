package com.edu.miu.repo;

import com.edu.miu.entity.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author gasieugru
 */
@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {

}
