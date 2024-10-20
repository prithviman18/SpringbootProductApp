package com.productApp.FirstProductApp.controller;


import com.productApp.FirstProductApp.dto.ForgotPasswordRequest;
import com.productApp.FirstProductApp.dto.ResetPasswordRequest;
import com.productApp.FirstProductApp.dto.SignupRequest;
import com.productApp.FirstProductApp.entity.User;
import com.productApp.FirstProductApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    // Register a new customer
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
        }

        User user = new User();
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setUsername(signupRequest.getUsername());
        user.setPassword(signupRequest.getPassword());
        user.setEmail(signupRequest.getEmail());
        user.setMobileNumber(signupRequest.getMobileNumber());

        User registeredUser = userService.registerCustomer(user);

        return ResponseEntity.ok(registeredUser);
    }

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Get user by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // Change user password (requires old and new password)
    @PutMapping("/users/{id}/changePassword")
    public ResponseEntity<?> changePassword(@PathVariable Long id,
                                            @RequestParam String oldPassword,
                                            @RequestParam String newPassword) {
        User updatedUser = userService.changePassword(id, oldPassword, newPassword);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        userService.forgotPassword(forgotPasswordRequest.getEmail());
        return "Password reset link has been sent to your email.";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        userService.resetPassword(resetPasswordRequest.getToken(), resetPasswordRequest.getNewPassword());
        return "Password has been successfully reset.";
    }


}
