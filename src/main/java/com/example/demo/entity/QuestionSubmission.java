package com.example.demo.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "question_submission")
public class QuestionSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_submission_id")
    private Long questionSubmissionId;

    @Column(name = "question_id")
    private Long questionId;
    // relationship with answers

    @Column(name = "answer")
    private String answer;

    @Column(name = "is_answer_correct")
    private Boolean isAnswerCorrect;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private TestSubmission testSubmission;

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

    public TestSubmission getTestSubmission() {
        return testSubmission;
    }

    public void setTestSubmission(TestSubmission testSubmission) {
        this.testSubmission = testSubmission;
    }


}
