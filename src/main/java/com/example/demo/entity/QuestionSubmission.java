package com.example.demo.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "question_submission")
public class QuestionSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    // relationship with answers

    @Column(name = "answer")
    private String answer;

    @Column(name = "is_answer_correct")
    private Boolean isAnswerCorrect;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private TestSubmission testSubmission;

    public Long getQuestionSubmissionId() {
        return questionSubmissionId;
    }

    public void setQuestionSubmissionId(Long id) {
        this.questionSubmissionId = questionSubmissionId;
    }

    public Long getQuestionId() {
        return id;
    }

    public void setQuestionId(Long id) {
        this.id = id;
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

    public TestSubmission getTestSubmission() {
        return testSubmission;
    }

    public void setTestSubmission(TestSubmission testSubmission) {
        this.testSubmission = testSubmission;
    }


}
