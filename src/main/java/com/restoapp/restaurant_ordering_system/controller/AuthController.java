package com.restoapp.restaurant_ordering_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.restoapp.restaurant_ordering_system.model.User;
import com.restoapp.restaurant_ordering_system.service.UserService;
import com.restoapp.restaurant_ordering_system.dto.AuthResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) { 
        // Basic input validation
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
            return ResponseEntity.badRequest().body("Missing required fields");
        }
        
        // Check if username already exists
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(registeredUser.getId(), registeredUser.getUsername(), registeredUser.getRole()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        return userService.findByUsername(loginRequest.getUsername())
            .filter(user -> passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            .map(user -> ResponseEntity.ok(new AuthResponse(user.getId(), user.getUsername(), user.getRole())))
            .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).<AuthResponse>build());
    }
}