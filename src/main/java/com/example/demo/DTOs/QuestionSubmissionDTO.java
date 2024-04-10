package com.example.demo.DTOs;

import com.example.demo.entity.QuestionSubmission;

public class QuestionSubmissionDTO {
    private Long questionSubmissionId;
    private Long questionId;
    private String answer;
    private Boolean isAnswerCorrect;
    public static QuestionSubmissionDTO mapQuestionSubmissionToQuestionSubmissionDTO(
            QuestionSubmission questionSubmission
    ) {
        QuestionSubmissionDTO questionSubmissionDTO = new QuestionSubmissionDTO();
        questionSubmissionDTO.setQuestionSubmissionId(questionSubmission.getQuestionSubmissionId());
        questionSubmissionDTO.setQuestionId(questionSubmission.getQuestionId());
        questionSubmissionDTO.setAnswer(questionSubmission.getAnswer());
        questionSubmissionDTO.setIsAnswerCorrect(questionSubmission.getIsAnswerCorrect());
        return questionSubmissionDTO;
    }

    public static QuestionSubmission mapQuestionSubmissionDTOToQuestionSubmission(
            QuestionSubmissionDTO questionSubmissionDTO
    ) {
        QuestionSubmission questionSubmission = new QuestionSubmission();
        questionSubmission.setQuestionSubmissionId(questionSubmissionDTO.getQuestionSubmissionId());
        questionSubmission.setQuestionId(questionSubmissionDTO.getQuestionId());
        questionSubmission.setAnswer(questionSubmissionDTO.getAnswer());
        questionSubmission.setIsAnswerCorrect(questionSubmissionDTO.getIsAnswerCorrect());
        return questionSubmission;
    }

    public Long getQuestionSubmissionId() {
        return questionSubmissionId;
    }

    public void setQuestionSubmissionId(Long id) {
        this.questionSubmissionId = questionSubmissionId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getIsAnswerCorrect() {
        return isAnswerCorrect;
    }

    public void setIsAnswerCorrect(Boolean answerCorrect) {
        isAnswerCorrect = answerCorrect;
    }

    public Boolean getAnswerCorrect() {
        return isAnswerCorrect;
    }

    public void setAnswerCorrect(Boolean answerCorrect) {
        isAnswerCorrect = answerCorrect;
    }

}
