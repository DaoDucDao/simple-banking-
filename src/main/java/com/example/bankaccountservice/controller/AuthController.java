package com.example.bankaccountservice.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankaccountservice.dto.AuthResponse;
import com.example.bankaccountservice.dto.LoginRequest;
import com.example.bankaccountservice.dto.RegisterRequest;
import com.example.bankaccountservice.entity.User;
import com.example.bankaccountservice.service.JWTService;
import com.example.bankaccountservice.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JWTService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenManager;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JWTService jwtService, UserService userService, AuthenticationManager authenManager,
            PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.authenManager = authenManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        String hashPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(hashPassword);
        user.setUsername(request.getUsername());
        user.setRole("USER");

        User newUser = userService.saveUser(user);

        return new AuthResponse(jwtService.generateToken(newUser), user.getUsername(), user.getEmail());
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        authenManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User loginUser = (User) userService.loadUserByUsername(request.getUsername());
        return new AuthResponse(jwtService.generateToken(loginUser), loginUser.getUsername(), loginUser.getEmail());
    }
}
