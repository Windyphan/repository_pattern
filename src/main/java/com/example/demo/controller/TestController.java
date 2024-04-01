package com.example.demo.controller;

import com.example.demo.entity.Test;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.JWTUtil;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private TestService testService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JWTUtil jwtUtil;

        // API endpoint to get a list of tests
        @GetMapping("/find")
        public List<Test> getAllTests(
                @RequestHeader("Authorization") String token) {
            try {
                authenticationService.authenticateToken(token);
                List<Test> tests = testService.getAllTests();
                return tests;
            } catch(IllegalArgumentException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage());
            }
        }

        @GetMapping("/find/{id}")
        public Test getTest(
                @PathVariable Long id,
                @RequestHeader("Authorization") String token) {
            try {
                authenticationService.authenticateToken(token);
                Test test = testService.getTest(id);
                return test;
            } catch(IllegalArgumentException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage());
            }
        }
    // Other API endpoints for test management
}