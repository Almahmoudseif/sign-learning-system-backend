package com.signlanguage.sign_learning_system.repository;

import com.signlanguage.sign_learning_system.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByAssessmentId(Long assessmentId);
}
