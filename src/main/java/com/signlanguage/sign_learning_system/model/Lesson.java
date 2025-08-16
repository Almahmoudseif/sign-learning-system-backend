package com.signlanguage.sign_learning_system.model;

import com.signlanguage.sign_learning_system.enums.LessonLevel;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "video_url")
    private String videoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LessonLevel level;

    @Column(name = "teacher_id")
    private Long teacherId;

    @Column(name = "lesson_type")  // video, picture, text
    private String lessonType;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "lesson-assessments")
    private List<Assessment> assessments;

    public Lesson() {}

    public Lesson(String title, String description, String imageUrl, String videoUrl, LessonLevel level, Long teacherId, String lessonType) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.level = level;
        this.teacherId = teacherId;
        this.lessonType = lessonType;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public LessonLevel getLevel() { return level; }
    public void setLevel(LessonLevel level) { this.level = level; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getLessonType() { return lessonType; }
    public void setLessonType(String lessonType) { this.lessonType = lessonType; }

    public List<Assessment> getAssessments() {
        return assessments;
    }
    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }

    @Transient
    public String getContentUrl() {
        if ("video".equalsIgnoreCase(lessonType)) {
            return videoUrl;
        } else if ("picture".equalsIgnoreCase(lessonType)) {
            return imageUrl;
        } else {
            return null; // text or other types might not have URL
        }
    }
}
