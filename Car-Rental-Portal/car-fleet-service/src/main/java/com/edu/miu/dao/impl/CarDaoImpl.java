package com.edu.miu.dao.impl;

import com.edu.miu.dao.CarDao;
import com.edu.miu.dto.CarDto;
import com.edu.miu.entity.Car;
import com.edu.miu.mapper.CarMapper;
import com.edu.miu.model.CarFilter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gasieugru
 */
@Repository
@RequiredArgsConstructor
public class CarDaoImpl implements CarDao {

    private final EntityManager em;

    private final CarMapper carMapper;

    @Override
    public List<CarDto> filterCars(CarFilter carFilter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Car> cq = cb.createQuery(Car.class);

        Root<Car> root = cq.from(Car.class);
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotBlank(carFilter.getModel())) {
            predicates.add(cb.equal(root.get("model"), carFilter.getModel()));
        }

        if (StringUtils.isNotBlank(carFilter.getMake())) {
            predicates.add(cb.equal(root.get("make"), carFilter.getMake()));
        }

        if (carFilter.getBaseCostPerDay() > 0) {
            predicates.add(cb.equal(root.get("baseCostPerDay"), carFilter.getBaseCostPerDay()));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        return carMapper.toListDto(em.createQuery(cq).getResultList());
    }
}
