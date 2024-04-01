package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.TestRepository;
import com.example.demo.repository.TestSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TestSubmissionService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestSubmissionRepository testSubmissionRepository;
    public TestSubmission submitTest(TestSubmission submission) {
        // Process the submission and save the test result
        if (submission == null) {
            throw new IllegalArgumentException("No data in submission");
        }
        Test test = testRepository.findByTestId(submission.getId());

        if (test == null) {
            throw new IllegalArgumentException("Illegal test id in test submission");
        }
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
        testSubmissionRepository.save(submission);
        return submission;
    }

    public List<TestSubmission> getTestResults(Long studentId) {
        // Query the database to get the test results for the specified student
        return testSubmissionRepository.findByStudentId(studentId); // Return the fetched test results
    }
}
