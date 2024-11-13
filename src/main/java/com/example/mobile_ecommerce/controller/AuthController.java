package com.example.mobile_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;

import com.example.mobile_ecommerce.model.User;
import com.example.mobile_ecommerce.service.UserService;
import com.example.mobile_ecommerce.service.AuthService;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-time}")
    private long jwtExpirationTime;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
     * Endpoint for user registration
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Validate input fields
        if (user.getUsername() == null || user.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Username and password are required",
                    "status", HttpStatus.BAD_REQUEST.value()));
        }

        // Check if the username already exists
        if (userService.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "message", "Username already exists",
                    "status", HttpStatus.CONFLICT.value()));
        }

        // Save user (no hashing)
        userService.addUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "User registered successfully",
                "status", HttpStatus.CREATED.value()));
    }

    /**
     * Endpoint for user login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        // Validate input fields
        if (user.getUsername() == null || user.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Username and password are required",
                    "status", HttpStatus.BAD_REQUEST.value()));
        }

        User existingUser = userService.findByUsername(user.getUsername());

        // Check if the user exists
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "message", "Invalid credentials",
                    "status", HttpStatus.UNAUTHORIZED.value()));
        }

        // Log the password in the database and entered password for debugging
        logger.debug("Entered password: " + user.getPassword());
        logger.debug("Stored password: " + existingUser.getPassword());

        // Check if the password matches (without password encoding)
        if (!user.getPassword().equals(existingUser.getPassword())) {
            logger.warn("Password mismatch for user: " + user.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "message", "Invalid credentials",
                    "status", HttpStatus.UNAUTHORIZED.value()));
        }

        // Generate JWT token
        String token = authService.generateToken(existingUser);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "status", HttpStatus.OK.value()));
    }
}
