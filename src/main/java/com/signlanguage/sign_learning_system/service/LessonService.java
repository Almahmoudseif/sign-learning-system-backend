package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.enums.LessonLevel;
import com.signlanguage.sign_learning_system.model.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonService {

    Lesson saveLesson(Lesson lesson);

    List<Lesson> getAllLessons();

    Optional<Lesson> getLessonById(Long id); // ✅ added

    List<Lesson> getLessonsByLevel(LessonLevel level);

    void deleteLesson(Long id);
}
