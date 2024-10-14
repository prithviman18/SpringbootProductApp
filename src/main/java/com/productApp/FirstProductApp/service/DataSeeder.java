package com.productApp.FirstProductApp.service;

import com.productApp.FirstProductApp.Enums.ERole;
import com.productApp.FirstProductApp.entity.Role;
import com.productApp.FirstProductApp.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public DataSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName(ERole.ROLE_CUSTOMER).isEmpty()) {
            Role customerRole = new Role();
            customerRole.setName(ERole.ROLE_CUSTOMER);
            roleRepository.save(customerRole);
        }

        if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName(ERole.ROLE_ADMIN);
            roleRepository.save(adminRole);
        }
    }
}
