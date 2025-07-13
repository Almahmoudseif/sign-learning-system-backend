package com.signlanguage.sign_learning_system.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student; // Assuming User with role STUDENT

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

    @Column(nullable = false)
    private Double score;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    @Column(name = "grade")
    private String grade;

    public Result() {}

    public Result(User student, Assessment assessment, Double score, LocalDateTime submittedAt, String grade) {
        this.student = student;
        this.assessment = assessment;
        this.score = score;
        this.submittedAt = submittedAt;
        this.grade = grade;
    }

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public Assessment getAssessment() { return assessment; }
    public void setAssessment(Assessment assessment) { this.assessment = assessment; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}
