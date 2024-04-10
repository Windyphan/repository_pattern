package com.example.demo.controller;

import com.example.demo.DTOs.TestDTO;
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
        public List<TestDTO> getAllTests(
                @RequestHeader("Authorization") String token) {
            try {
                authenticationService.authenticateToken(token);
                List<TestDTO> testDTOs = testService.getAllTests();
                return testDTOs;
            } catch(IllegalArgumentException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage());
            }
        }

        @GetMapping("/find/{id}")
        public TestDTO getTest(
                @PathVariable Long id,
                @RequestHeader("Authorization") String token) {
            try {
                authenticationService.authenticateToken(token);
                TestDTO testDTO = testService.getTest(id);
                return testDTO;
            } catch(IllegalArgumentException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage());
            }
        }
    // Other API endpoints for test management
}