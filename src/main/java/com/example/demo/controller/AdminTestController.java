package com.example.demo.controller;

import com.example.demo.entity.Test;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.JWTUtil;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/test")
public class AdminTestController {
    @Autowired
    private TestService testService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationService authenticationService;

    public enum UserRole{
        TEACHER,
        STUDENT
    }

    // API endpoint to create a new test
    @PostMapping("/create")
    @PreAuthorize("hasRole(T(UserRole).TEACHER)")
    public Test createTest(
            @RequestHeader("Authorization") String token,
            @RequestBody Test test) {
        try {
            authenticationService.authenticateToken(token);
            testService.saveTest(test);
            return test;
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage());
        }
    }

    //update a test (teacher)
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole(T(UserRole).TEACHER)")
    public void updateTest(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody Test test
    ) {
        try {
            authenticationService.authenticateToken(token);
            testService.updateTest(id, test);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage());
        }
    }

    // Delete a test (Teacher)
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole(T(UserRole).TEACHER)")
    public void deleteTest(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id
    ) {
        try {
            authenticationService.authenticateToken(token);
            testService.deleteTest(id);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage());
        }
    }
}
