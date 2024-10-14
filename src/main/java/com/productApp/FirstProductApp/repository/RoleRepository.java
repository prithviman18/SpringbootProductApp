package com.productApp.FirstProductApp.repository;

import com.productApp.FirstProductApp.Enums.ERole;
import com.productApp.FirstProductApp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    // Custom query to find a role by its name
    Optional<Role> findByName(ERole name);
}
