package com.edu.miu.repo;

import com.edu.miu.entity.Maintenance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author gasieugru
 */
@Repository
public interface MaintenanceRepository extends CrudRepository<Maintenance, Integer> {
}
