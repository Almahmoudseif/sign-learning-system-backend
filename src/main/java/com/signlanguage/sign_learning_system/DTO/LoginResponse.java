package com.signlanguage.sign_learning_system.DTO;

public class LoginResponse {
    private Long id;
    private String registrationNumber;
    private String fullName;
    private String role;
    private String level;

    public LoginResponse(Long id, String registrationNumber, String fullName, String role, String level) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.fullName = fullName;
        this.role = role;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    public String getLevel() {
        return level;
    }
}
