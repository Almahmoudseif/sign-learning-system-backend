package com.signlanguage.sign_learning_system.repository;

import com.signlanguage.sign_learning_system.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestionId(Long questionId);
}
