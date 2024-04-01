package com.example.demo.controller;

import com.example.demo.entity.TestSubmission;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.JWTUtil;
import com.example.demo.service.StudentService;
import com.example.demo.service.TestSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/{studentId}/test/{testId}/submit")
    @PreAuthorize("hasRole(T(UserRole).STUDENT)")
    public TestSubmission submitTest(
            @RequestHeader("Authorization") String token,
            @RequestBody Long studentId,
            @RequestBody TestSubmission submission
    ) {
        authenticationService.authenticateToken(token);
        submission.setId(studentId);
        testSubmissionService.submitTest(submission);
        return submission;
    }

    @GetMapping("/{studentId}/test-results")
    @PreAuthorize("hasRole(T(UserRole).STUDENT)")
    public List<TestSubmission> getTestResults(
            @RequestHeader("Authorization") String token,
            @PathVariable Long studentId) {
        authenticationService.authenticateToken(token);
        List<TestSubmission> testResults = testSubmissionService.getTestResults(studentId);
        return testResults;
    }
}
