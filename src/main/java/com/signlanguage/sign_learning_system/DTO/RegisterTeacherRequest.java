package com.signlanguage.sign_learning_system.DTO;// package: com.signlanguage.sign_learning_system.dto

public class RegisterTeacherRequest {
    private String fullName;
    private String email;
    private String password;

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }
    public class LoginRequest {
        private String registrationNumber;
        private String password;

        // getters and setters
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
