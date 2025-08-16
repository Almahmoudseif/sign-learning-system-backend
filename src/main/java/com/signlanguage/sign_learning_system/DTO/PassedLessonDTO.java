package com.signlanguage.sign_learning_system.DTO;

import java.time.LocalDateTime;

public class PassedLessonDTO {
    private Long lessonId;
    private String lessonTitle;
    private String lessonLevel;  // Hapa ni String, sio enum
    private String lessonType; // video, picture, text
    private String contentUrl;
    private Double score;
    private String grade;
    private LocalDateTime datePassed;

    public PassedLessonDTO(Long lessonId, String lessonTitle, String lessonLevel,
                           String lessonType, String contentUrl, Double score,
                           String grade, LocalDateTime datePassed) {
        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
        this.lessonLevel = lessonLevel;
        this.lessonType = lessonType;
        this.contentUrl = contentUrl;
        this.score = score;
        this.grade = grade;
        this.datePassed = datePassed;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public String getLessonLevel() {
        return lessonLevel;
    }

    public String getLessonType() {
        return lessonType;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public Double getScore() {
        return score;
    }

    public String getGrade() {
        return grade;
    }

    public LocalDateTime getDatePassed() {
        return datePassed;
    }
}
