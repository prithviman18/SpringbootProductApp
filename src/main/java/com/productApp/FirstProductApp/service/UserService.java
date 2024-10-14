package com.productApp.FirstProductApp.service;

import com.productApp.FirstProductApp.Enums.ERole;
import com.productApp.FirstProductApp.entity.Role;
import com.productApp.FirstProductApp.entity.User;
import com.productApp.FirstProductApp.repository.RoleRepository;
import com.productApp.FirstProductApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Register a new user (Customer)
    public User registerCustomer(User user) {
        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assign default role as CUSTOMER
        Role customerRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);
        user.setRoles(roles);

        // Save the user to the database
        return userRepository.save(user);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by id
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
    }

    // Change user password
    public User changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        // Check if the old password matches the current one
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Error: Old password is incorrect.");
        }

        // Encrypt the new password
        user.setPassword(passwordEncoder.encode(newPassword));

        // Save the updated user
        return userRepository.save(user);
    }

}
