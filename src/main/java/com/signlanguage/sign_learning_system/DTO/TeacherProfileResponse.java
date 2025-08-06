package com.signlanguage.sign_learning_system.DTO;

public class TeacherProfileResponse {
    private Long id;
    private String fullName;
    private String email;
    private String registrationNumber;

    public TeacherProfileResponse(Long id, String fullName, String email, String registrationNumber) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.registrationNumber = registrationNumber;
    }

    public Long getId() {
        return id;
    }
    public String getFullName() {
        return fullName;
    }
    public String getEmail() {
        return email;
    }
    public String getRegistrationNumber() {
        return registrationNumber;
    }
}
