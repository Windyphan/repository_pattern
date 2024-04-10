package com.example.demo.controller;

import com.example.demo.DTOs.TestSubmissionDTO;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.JWTUtil;
import com.example.demo.service.StudentService;
import com.example.demo.service.TestSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TestSubmissionService testSubmissionService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationService authenticationService;

    public enum UserRole{
        TEACHER,
        STUDENT
    }

    // API endpoint to allow students to take tests
    @PostMapping("/test/submit")
    @PreAuthorize("hasRole(T(UserRole).STUDENT)")
    public TestSubmissionDTO submitTest(
            @RequestHeader("Authorization") String token,
            @RequestBody TestSubmissionDTO submission
    ) {
        try{
            authenticationService.authenticateToken(token);
            testSubmissionService.submitTest(submission);
            return submission;
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage());
        }
    }

    @GetMapping("/{studentId}/test-results")
    @PreAuthorize("hasRole(T(UserRole).STUDENT)")
    public List<TestSubmissionDTO> getTestResults(
            @RequestHeader("Authorization") String token,
            @PathVariable Long studentId) {
        try{
            authenticationService.authenticateToken(token);
            List<TestSubmissionDTO> testResults = testSubmissionService.getTestResults(studentId);
            return testResults;
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage());
        }
    }
}
