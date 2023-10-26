package com.edu.miu.repo;

import com.edu.miu.entity.User;
import com.edu.miu.enums.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gasieugru
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * Find user by userId
     * @param userId
     * @return User
     */
    User findByUserId(Integer userId);

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

    /**
     * Find user by roles
     * @param role
     * @return Users
     */
    List<User> findByUserRole(Role role);

    /**
     * Find user by roles
     * @param roles
     * @return Users
     */
    List<User> findByUserRoleIn(List<Role> roles);

}
