package com.example.demo.service;
import com.example.demo.entity.Answer;
import com.example.demo.entity.Question;
import com.example.demo.entity.Test;
import com.example.demo.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public List<Test> getAllTests() {
        List<Test> tests = testRepository.findAll();
        if (tests == null) {
            throw new IllegalArgumentException("Tests is not existed in database");
        }
        return tests;
    }

    public Test getTest(Long id) {
        Test test = testRepository.findByTestId(id);
        if (test == null) {
            throw new IllegalArgumentException("Test is not existed in database");
        }
        return test;
    }

    public void saveTest(Test test) {
        if (test.getName() == null || test.getName().isEmpty()) {
            throw new IllegalArgumentException("Test must have at least a name");
        }
        if(test.getDescription() == null || test.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Test must have at least a description");
        }
        if (test.getQuestions() == null ||test.getQuestions().isEmpty()) {
            throw new IllegalArgumentException("Test must have at least a question");
        }


        // Validate each question in the test
        boolean allQuestionsValid = test.getQuestions().stream()
                .allMatch(question -> question.getAnswers() != null && !question.getAnswers().isEmpty()
                        && question.getAnswers().stream().anyMatch(Answer::isCorrect));

        if (!allQuestionsValid) {
            throw new IllegalArgumentException("Each question must have at least one answer and one correct answer");
        }
        Test existingTest = testRepository.findByTestId(test.getTestId());
        if (existingTest != null) {
            throw new IllegalArgumentException("Test already existed");
        }
        testRepository.save(test);
    }

    public void updateTest(Long id, Test updatedTest) {
        Test existingTest = testRepository.findByTestId(id);
        if (existingTest == null) {
            throw new IllegalArgumentException("Test not existing");
        }
        if (updatedTest.getName() != null || !updatedTest.getName().isEmpty()) {
            existingTest.setName(updatedTest.getName());
        }
        if(updatedTest.getDescription() != null || !updatedTest.getDescription().isEmpty()) {
            existingTest.setDescription(updatedTest.getDescription());
        }
        if (updatedTest.getQuestions() != null && !updatedTest.getQuestions().isEmpty()) {
            for (Question question : updatedTest.getQuestions()) {
                // Update only if there is a value
                if (question.getId() == null || updatedTest.getQuestions().isEmpty()) {
                    throw new IllegalArgumentException("Question id is required");
                }
                existingTest.getQuestions().stream()
                        .filter(existingQuestion -> existingQuestion.getId().equals(question.getId())) // Assuming question ID is unique
                        .findFirst()
                        .ifPresent(existingQuestion -> {
                            if (question.getContent() != null && !question.getContent().isEmpty()) {
                                existingQuestion.setContent(question.getContent());
                            }
                            if (question.getAnswers() != null && !question.getAnswers().isEmpty()) {
                                for (Answer answer : question.getAnswers()) {
                                    if (answer.getId() == null || updatedTest.getQuestions().isEmpty()) {
                                        throw new IllegalArgumentException("Answer id is required");
                                    }
                                    existingQuestion.getAnswers().stream()
                                            .filter(existingAnswer -> existingAnswer.getId().equals(answer.getId())) // Assuming question ID is unique
                                            .findFirst()
                                            .ifPresent(existingAnswer -> {
                                                if (answer.getContent() != null && !answer.getContent().isEmpty()) {
                                                    // Update only if there is a value
                                                    existingAnswer.setContent(answer.getContent());
                                                }
                                            });
                                }
                            }

                        });
            }
        }

        testRepository.save(existingTest);
    }

    public void deleteTest(Long id) {
        Test existingTest = testRepository.findByTestId(id);
        if (existingTest == null) {
            throw new IllegalArgumentException("Test not existing");
        }
        testRepository.delete(existingTest);
    }

}