package com.productApp.FirstProductApp.service;

import com.productApp.FirstProductApp.Enums.ERole;
import com.productApp.FirstProductApp.entity.PasswordResetToken;
import com.productApp.FirstProductApp.entity.Role;
import com.productApp.FirstProductApp.entity.User;
import com.productApp.FirstProductApp.repository.PasswordResetTokenRepository;
import com.productApp.FirstProductApp.repository.RoleRepository;
import com.productApp.FirstProductApp.repository.UserRepository;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    private int expiryTimeInMinutes = 60;

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
        User savedUser = userRepository.save(user);
        mailService.sendWelcomeEmail(savedUser.getEmail(), savedUser.getUsername());
        return savedUser;
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

    public void forgotPassword(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Error: User with email " + email + " not found."));

        //Generate a token
        String token = UUID.randomUUID().toString();

        // Create token object and save it to the database
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(calculateExpiryDate(expiryTimeInMinutes));
        passwordResetTokenRepository.save(passwordResetToken);
        // Send email with the reset link
        String resetLink = "http://your-app-url/reset-password?token=" + token;
        mailService.sendPasswordResetEmail(user.getEmail(), resetLink);

    }

    // Calculate expiry date for the reset token
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }

    // Validate the token and reset the password
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Error: Invalid reset token."));

        if (resetToken.getExpiryDate().before(new Date())) {
            throw new RuntimeException("Error: Token has expired.");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Optionally, you can delete the token after the password is reset
        passwordResetTokenRepository.delete(resetToken);
    }

    //Register admin
    public User registerAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //Assigning the role of admin
        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Admin role not found"));

        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        user.setRoles(roles);

        //save the user to database
        User savedUser = userRepository.save(user);
        mailService.sendWelcomeEmail(savedUser.getEmail(), savedUser.getUsername());
        return savedUser;
    }

    // Register a new Employee
    public User registerEmployee(User user) {
        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assign default role as EMPLOYEE
        Role employeeRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        Set<Role> roles = new HashSet<>();
        roles.add(employeeRole);
        user.setRoles(roles);

        // Save the user to the database
        User savedUser = userRepository.save(user);
        mailService.sendWelcomeEmail(savedUser.getEmail(), savedUser.getUsername());
        return savedUser;
    }

}
