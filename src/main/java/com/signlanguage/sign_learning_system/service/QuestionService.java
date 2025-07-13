package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.model.Question;
import com.signlanguage.sign_learning_system.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public List<Question> getQuestionsByAssessmentId(Long assessmentId) {
        return questionRepository.findByAssessmentId(assessmentId);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
