package com.signlanguage.sign_learning_system.repository;

import com.signlanguage.sign_learning_system.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    // Hii sasa inatafuta Answers kwa assessment id kupitia question.assessment.id
    List<Answer> findByQuestion_Assessment_Id(Long assessmentId);

    List<Answer> findByQuestionId(Long questionId);

    // Ikiwa unahitaji kulingana na student (kama umeongeza student field Answer), unaweza kuongeza

    // Optional: if you are finding correct answer for a question
    Optional<Answer> findByQuestionIdAndIsCorrectTrue(Long questionId);

    // Ondoa hii ifuatayo ili kuepuka method duplicates:
    // Optional<Answer> findByQuestionIdAndCorrectTrue(Long id);
}
