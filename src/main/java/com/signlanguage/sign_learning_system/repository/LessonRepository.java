package com.signlanguage.sign_learning_system.repository;

import com.signlanguage.sign_learning_system.enums.LessonLevel;
import com.signlanguage.sign_learning_system.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByLevel(LessonLevel level);
}
