package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.model.Answer;

import java.util.List;
import java.util.Optional;

public interface AnswerService {
    Answer saveAnswer(Answer answer);

    List<Answer> getAllAnswers();

    Optional<Answer> getAnswerById(Long id);

    List<Answer> getAnswersByQuestionId(Long questionId);

    void deleteAnswer(Long id);
}
