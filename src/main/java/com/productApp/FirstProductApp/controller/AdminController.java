package com.productApp.FirstProductApp.controller;

import com.productApp.FirstProductApp.entity.User;
import com.productApp.FirstProductApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @PostMapping("/registerAdmin")
    public ResponseEntity<User> registerAdmin(@RequestBody User user){
        User admin = userService.registerAdmin(user);
        return new ResponseEntity<>(admin, HttpStatus.CREATED);
    }

    // Register a new Employee and return the created user details
    @PostMapping("/registerEmployee")
    public ResponseEntity<User> registerEmployee(@RequestBody User user) {
        User employee = userService.registerEmployee(user);
        return new ResponseEntity<>(employee, HttpStatus.CREATED); // Returning the created user details
    }
}
