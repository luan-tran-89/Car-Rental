package com.edu.miu.repo;

import com.edu.miu.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author gasieugru
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);

}
