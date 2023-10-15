package com.edu.miu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.edu.miu.domain.Rental;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {

    List<Rental> findByUserId(Integer userId);

    List<Rental> findByCarId(Integer carId);

    Optional<Rental> findByCarIdAndEndDateAfter(Integer carId, Date today);

    List<Rental> findByUserIdAndEndDateAfter(Integer userId, Date today);
}
