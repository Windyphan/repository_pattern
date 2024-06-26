package com.example.demo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "test_submissions")
public class TestSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "student_id")
    private Long studentId;
    @Column(name = "test_id")
    private Long testId;
    @OneToMany(mappedBy = "testSubmission", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<QuestionSubmission> questionSubmissions;
    @Column(name = "time_submission")
    private LocalDateTime submissionTime;

    public TestSubmission() {
        // Default constructor
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


    public List<QuestionSubmission> getQuestionSubmissions() {
        return questionSubmissions;
    }

    public void setQuestionSubmissions(List<QuestionSubmission> questionSubmissions) {
        this.questionSubmissions = questionSubmissions;
    }

    // Getters and Setters
}