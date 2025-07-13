package com.signlanguage.sign_learning_system.model;

import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String staffNumber;

    // Constructors
    public Teacher() {}

    public Teacher(String name, String email, String password, String staffNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.staffNumber = staffNumber;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }
}
