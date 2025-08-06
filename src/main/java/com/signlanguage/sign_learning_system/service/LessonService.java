package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.enums.LessonLevel;
import com.signlanguage.sign_learning_system.model.Lesson;

import java.util.List;

public interface LessonService {

    Lesson saveLesson(Lesson lesson);

    List<Lesson> getAllLessons();

    Lesson getLessonById(Long id);

    List<Lesson> getLessonsByLevel(LessonLevel level);

    // Badilisha return type kutoka void kuwa boolean
    boolean deleteLesson(Long id);

    Lesson updateLessonImage(Long id, String imageUrl);

    List<Lesson> getVideoLessonsOnly();

    List<Lesson> findByLevel(LessonLevel level);

    // Method mpya ili kupata lessons kwa teacherId
    List<Lesson> getLessonsByTeacherId(Long teacherId);

    List<Lesson> getLessonsByLevelAndTeacherId(LessonLevel level, Long teacherId);
}
