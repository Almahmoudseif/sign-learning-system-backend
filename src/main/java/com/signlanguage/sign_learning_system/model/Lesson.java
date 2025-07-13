package com.signlanguage.sign_learning_system.model;

import com.signlanguage.sign_learning_system.enums.LessonLevel;
import jakarta.persistence.*;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 5000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LessonLevel level;

    // Constructors, getters and setters (omitted here for brevity)
}
