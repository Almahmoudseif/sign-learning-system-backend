package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Question saveQuestion(Question question);
    List<Question> getAllQuestions();
    Optional<Question> getQuestionById(Long id);
    List<Question> getQuestionsByAssessmentId(Long assessmentId);
    Optional<Question> updateQuestion(Long id, Question question);
    boolean deleteQuestion(Long id);
}
