package com.signlanguage.sign_learning_system.DTO;

import org.springframework.web.multipart.MultipartFile;

public class UploadLessonRequest {

    private String title;
    private String description;
    private String level;  // Au unaweza tumia enum kama LessonLevel
    private MultipartFile image;
    private MultipartFile video;

    // Getters na Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public MultipartFile getVideo() {
        return video;
    }

    public void setVideo(MultipartFile video) {
        this.video = video;
    }
}
