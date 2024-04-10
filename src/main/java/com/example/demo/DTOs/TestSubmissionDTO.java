package com.example.demo.DTOs;

import com.example.demo.entity.QuestionSubmission;
import com.example.demo.entity.TestSubmission;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TestSubmissionDTO {
    private Long id;
    private Long studentId;
    private Long testId;
    private List<QuestionSubmissionDTO> questionSubmissions;
    private LocalDateTime submissionTime;

    public TestSubmissionDTO mapTestSubmissionToTestSubmissionDTO(TestSubmission testSubmission) {
        TestSubmissionDTO testSubmissionDTO = new TestSubmissionDTO();
        testSubmissionDTO.setId(testSubmission.getStudentId());
        testSubmissionDTO.setStudentId(testSubmission.getTestId());
        testSubmissionDTO.setTestId(testSubmission.getTestId());
        testSubmissionDTO.setSubmissionTime(testSubmission.getSubmissionTime());

        List<QuestionSubmissionDTO> questionSubmissionDTOList = testSubmission.getQuestionSubmissions().stream()
                .map(QuestionSubmissionDTO::mapQuestionSubmissionToQuestionSubmissionDTO)
                .collect(Collectors.toList());
        testSubmissionDTO.setQuestionSubmissions(questionSubmissionDTOList);

        return testSubmissionDTO;
    }

    public TestSubmission mapTestSubmissionDTOToTestSubmission(TestSubmissionDTO testSubmissionDTO) {
        TestSubmission testSubmission = new TestSubmission();
        testSubmission.setId(testSubmissionDTO.getStudentId());
        testSubmission.setStudentId(testSubmissionDTO.getTestId());
        testSubmission.setTestId(testSubmissionDTO.getTestId());
        testSubmission.setSubmissionTime(testSubmissionDTO.getSubmissionTime());

        List<QuestionSubmission> questionSubmissionList = testSubmissionDTO.getQuestionSubmissions().stream()
                .map(QuestionSubmissionDTO::mapQuestionSubmissionDTOToQuestionSubmission)
                .collect(Collectors.toList());
        testSubmission.setQuestionSubmissions(questionSubmissionList);

        return testSubmission;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(LocalDateTime submissionTime) {
        this.submissionTime = submissionTime;
    }


    public List<QuestionSubmissionDTO> getQuestionSubmissions() {
        return questionSubmissions;
    }

    public void setQuestionSubmissions(List<QuestionSubmissionDTO> questionSubmissions) {
        this.questionSubmissions = questionSubmissions;
    }

    // Getters and Setters
}