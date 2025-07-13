package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.model.Answer;
import com.signlanguage.sign_learning_system.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    public Answer saveAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    public Optional<Answer> getAnswerById(Long id) {
        return answerRepository.findById(id);
    }

    public List<Answer> getAnswersByQuestionId(Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }
}
