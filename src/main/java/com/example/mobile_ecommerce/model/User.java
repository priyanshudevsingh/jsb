package com.example.mobile_ecommerce.model;

import java.util.UUID;

public class User {
    private String user_id;
    private String username;
    private String password;

    // Constructor with validation
    public User(String username, String password) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        this.user_id = UUID.randomUUID().toString().replaceAll("-", "");
        this.username = username;
        this.password = password; // No hashing here
    }

    // Default constructor
    public User() {
    }

    // Getters and Setters
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password; // No hashing here
    }
}
