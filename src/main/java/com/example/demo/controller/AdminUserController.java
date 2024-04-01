package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.JWTUtil;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class AdminUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JWTUtil jwtUtil;
    public enum UserRole{
        TEACHER,
        STUDENT
    }
    // Create a new user
    @PostMapping("/create")
    @PreAuthorize("hasRole(T(UserRole).TEACHER)")
    public User createUser(
            @RequestHeader("Authorization") String token,
            @RequestBody User user) {
        try {
            authenticationService.authenticateToken(token);
            userService.createUser(user);
            return user;
        } catch(IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage());
        }
    }

    // Get all users (Teachers and Students)
    @GetMapping("/find")
    @PreAuthorize("hasRole(T(UserRole).TEACHER)")
    public List<User> getAllUsers(
            @RequestHeader("Authorization") String token
    ) {
        authenticationService.authenticateToken(token);
        return userService.getAllUsers();
    }

    // Get all users (Teachers and Students)
    @GetMapping("/find/{id}")
    @PreAuthorize("hasRole(T(UserRole).TEACHER)")
    public User getUser(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        try {
            authenticationService.authenticateToken(token);
            return userService.findUser(id);
        } catch(IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage());
        }
    }

    // Update a user (Teacher)
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole(T(UserRole).TEACHER)")
    public void updateUser(
            @RequestHeader("Authorization") String token,
            @RequestBody User user,
            @PathVariable Long id) {
        try {
            authenticationService.authenticateToken(token);
            userService.updateUser(id, user);
        } catch(IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage());
        }

    }

    // Delete a user (Teacher)

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole(T(UserRole).TEACHER)")
    public void deleteUser(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        try {
            authenticationService.authenticateToken(token);
            userService.deleteUser(id);
        } catch(IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage());
        }
    }

}