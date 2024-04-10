package com.example.demo.DTOs;

import com.example.demo.entity.Answer;

public class AnswerDTO {
    private Long id;
    private String content;
    private boolean correct;

    public static AnswerDTO mapAnswerToAnswerDTO(Answer answer) {
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setId(answer.getId());
        answerDTO.setContent(answer.getContent());
        answerDTO.setCorrect(answer.isCorrect());

        return answerDTO;
    }

    public static Answer mapAnswerDTOToAnswer(AnswerDTO answerDTO) {
        Answer answer = new Answer();
        answer.setId(answerDTO.getId());
        answer.setContent(answerDTO.getContent());
        answer.setCorrect(answerDTO.isCorrect());

        return answer;
    }

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

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
