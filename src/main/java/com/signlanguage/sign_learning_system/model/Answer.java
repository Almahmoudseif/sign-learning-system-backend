package com.signlanguage.sign_learning_system.model;

import jakarta.persistence.*;

@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    public Answer() {}

    public Answer(String content, boolean isCorrect, Question question) {
        this.content = content;
        this.isCorrect = isCorrect;
        this.question = question;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public boolean isCorrect() { return isCorrect; }
    public void setCorrect(boolean isCorrect) { this.isCorrect = isCorrect; }

    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }
}
