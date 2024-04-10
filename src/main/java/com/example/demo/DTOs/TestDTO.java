package com.example.demo.DTOs;

import com.example.demo.entity.Question;
import com.example.demo.entity.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestDTO {
    private Long testId;
    private String name;
    private String description;
    private List<QuestionDTO> questions;

    public TestDTO mapTestToTestDTO(Test test) {
        TestDTO testDTO = new TestDTO();
        testDTO.setTestId(test.getTestId());
        testDTO.setName(test.getName());
        testDTO.setDescription(test.getDescription());

        List<QuestionDTO> questionDTOList = test.getQuestions().stream()
                .map(QuestionDTO::mapQuestionToQuestionDTO)
                .collect(Collectors.toList());
        testDTO.setQuestions(questionDTOList);

        return testDTO;
    }

    public Test mapTestDTOToTest(TestDTO testDTO) {
        Test test = new Test();
        test.setTestId(testDTO.getTestId());
        test.setName(testDTO.getName());
        test.setDescription(testDTO.getDescription());

        List<Question> questionList = testDTO.getQuestions().stream()
                .map(QuestionDTO::mapQuestionDTOToQuestion)
                .collect(Collectors.toList());
        Set<Question> questionSet = new HashSet<>(questionList);
        test.setQuestions(questionSet);

        return test;
    }

    // Getters and Setters
    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

}
