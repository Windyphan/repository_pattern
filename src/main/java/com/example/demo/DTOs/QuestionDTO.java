package com.example.demo.DTOs;

import com.example.demo.entity.Answer;
import com.example.demo.entity.Question;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class QuestionDTO {
    private Long id;
    private String content;
    private List<AnswerDTO> answers;

    public static QuestionDTO mapQuestionToQuestionDTO(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setContent(question.getContent());

        List<AnswerDTO> answerDTOList = question.getAnswers().stream()
                .map(AnswerDTO::mapAnswerToAnswerDTO)
                .collect(Collectors.toList());
        questionDTO.setAnswers(answerDTOList);

        return questionDTO;
    }

    public static Question mapQuestionDTOToQuestion(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setContent(questionDTO.getContent());

        List<Answer> answerList = questionDTO.getAnswers().stream()
                .map(AnswerDTO::mapAnswerDTOToAnswer)
                .collect(Collectors.toList());
        Set<Answer> answerSet = new HashSet<>(answerList);
        question.setAnswers(answerSet);

        return question;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }

}
