package com.signlanguage.sign_learning_system.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_responses")
public class StudentResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer selectedAnswer;

    @Column(name = "response_time", nullable = false)
    private LocalDateTime responseTime;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    public StudentResponse() {}

    public StudentResponse(User student, Question question, Answer selectedAnswer, LocalDateTime responseTime, Boolean isCorrect) {
        this.student = student;
        this.question = question;
        this.selectedAnswer = selectedAnswer;
        this.responseTime = responseTime;
        this.isCorrect = isCorrect;
    }

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }

    public Answer getSelectedAnswer() { return selectedAnswer; }
    public void setSelectedAnswer(Answer selectedAnswer) { this.selectedAnswer = selectedAnswer; }

    public LocalDateTime getResponseTime() { return responseTime; }
    public void setResponseTime(LocalDateTime responseTime) { this.responseTime = responseTime; }

    public Boolean getIsCorrect() { return isCorrect; }
    public void setIsCorrect(Boolean isCorrect) { this.isCorrect = isCorrect; }
}
