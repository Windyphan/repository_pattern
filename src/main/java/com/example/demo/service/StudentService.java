package com.example.demo.service;


import com.example.demo.entity.TestSubmission;
import com.example.demo.repository.TestSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private TestSubmissionRepository testSubmissionRepository;

    public List<TestSubmission> getTestResultsForStudent(Long studentId) {
        return testSubmissionRepository.findByStudentId(studentId);
    }
}
