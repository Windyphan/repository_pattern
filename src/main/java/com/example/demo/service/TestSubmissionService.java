package com.example.demo.service;

import com.example.demo.DTOs.TestSubmissionDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.TestRepository;
import com.example.demo.repository.TestSubmissionRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TestSubmissionService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestSubmissionRepository testSubmissionRepository;

    public void submitTest(TestSubmissionDTO submissionDTO) {
        // Process the submission and save the test result
        if (submissionDTO == null) {
            throw new IllegalArgumentException("No data in submission");
        }
        User student = userRepository.findByUserId(submissionDTO.getStudentId());
        if (student == null) {
            throw new IllegalArgumentException("User id not existed");
        }
        if (student.getRole() == UserRole.TEACHER) {
            throw new IllegalArgumentException("User role is forbidden");
        }
        Test test = testRepository.findTestChildEntitiesByTestId(submissionDTO.getTestId());

        if (test == null) {
            throw new IllegalArgumentException("Illegal test id in test submission");
        }
        TestSubmission submission = submissionDTO.mapTestSubmissionDTOToTestSubmission(submissionDTO);
        // Create a set of question IDs from the test
        Set<Long> testQuestionIds = test.getQuestions().stream()
                .map(Question::getId)
                .collect(Collectors.toSet());

        // Check if the submitted question IDs match the test question IDs
        Set<Long> submittedQuestionIds = submission.getQuestionSubmissions().stream()
                .map(QuestionSubmission::getQuestionId)
                .collect(Collectors.toSet());

        if (!testQuestionIds.equals(submittedQuestionIds)) {
            throw new IllegalArgumentException("Submission must include answers for all questions in the test");
        }

        // Proceed with checking the correctness of the answers
        for (QuestionSubmission questionSubmission : submission.getQuestionSubmissions()) {
            questionSubmission.setTestSubmission(submission);
            // Retrieve the corresponding Question and compare answers
            Question question = test.getQuestions().stream()
                    .filter(q -> q.getId().equals(questionSubmission.getQuestionId()))
                    .findFirst()
                    .orElse(null);
            if (question != null) {
                // Logic to compare answers, etc.
                Answer answer = question.getAnswers().stream()
                        .filter(q -> q.getContent().equals(questionSubmission.getAnswer()) && q.isCorrect())
                        .findFirst()
                        .orElse(null);
                if (answer == null) {
                    questionSubmission.setIsAnswerCorrect(false);
                } else {
                    questionSubmission.setIsAnswerCorrect(true);
                }
            }
        }
        submission.setSubmissionTime(LocalDateTime.now());
        testSubmissionRepository.save(submission);
    }

    public List<TestSubmissionDTO> getTestResults(Long studentId) {
        // Query the database to get the test results for the specified student
        List<TestSubmission> submissions = testSubmissionRepository.findByStudentId(studentId);
        if (submissions == null) {
            throw new IllegalArgumentException("No data in submission");
        }
        List<TestSubmissionDTO> submissionDTOS = new ArrayList<>();
        for (TestSubmission submission : submissions) {
            TestSubmissionDTO submissionDTO = new TestSubmissionDTO();
            submissionDTO = submissionDTO.mapTestSubmissionToTestSubmissionDTO(submission);
            submissionDTOS.add(submissionDTO);
        }
        return submissionDTOS; // Return the fetched test results
    }
}
