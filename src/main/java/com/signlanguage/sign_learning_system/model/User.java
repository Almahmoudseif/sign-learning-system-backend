package com.signlanguage.sign_learning_system.model;

import jakarta.persistence.*;
import com.signlanguage.sign_learning_system.enums.LessonLevel;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String registrationNumber;

    private String password;
    private String role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private LessonLevel level;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LessonLevel getLevel() { return level; }
    public void setLevel(LessonLevel level) { this.level = level; }
}
