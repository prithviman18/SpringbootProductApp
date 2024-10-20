package com.productApp.FirstProductApp.controller;


import com.productApp.FirstProductApp.dto.JwtResponse;
import com.productApp.FirstProductApp.dto.LoginRequest;
import com.productApp.FirstProductApp.dto.LoginResponse;
import com.productApp.FirstProductApp.service.AuthService;
import com.productApp.FirstProductApp.service.UserService;
import com.productApp.FirstProductApp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }


}
