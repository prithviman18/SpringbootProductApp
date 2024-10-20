package com.productApp.FirstProductApp.repository;

import com.productApp.FirstProductApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    // Custom query to find a user by username
    Optional<User> findByUsername(String username);

    // Custom query to find a user by email
    Optional<User> findByEmail(String email);

    // Check if the username already exists
    Boolean existsByUsername(String username);

    // Check if the email already exists
    Boolean existsByEmail(String email);

    // Custom query to find users with ROLE_CUSTOMER
    List<User> findByRoles_Name(String roleName);


}
