package com.edu.miu.repo;

import com.edu.miu.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author gasieugru
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * Find user by email
     * @param email
     * @return User
     */
    User findByEmail(String email);

    /**
     * Check user exists
     * @param email
     * @return true if user exists, otherwise false
     */
    boolean existsByEmail(String email);

}
