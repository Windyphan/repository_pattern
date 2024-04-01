package com.example.demo.service;


import com.example.demo.entity.TestSubmission;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TestSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestSubmissionRepository testSubmissionRepository;

    @Autowired
    private TestService testService;



    public List<TestSubmission> getTestResultsForStudent(Long studentId) {
        return testSubmissionRepository.findByStudentId(studentId);
    }
}
