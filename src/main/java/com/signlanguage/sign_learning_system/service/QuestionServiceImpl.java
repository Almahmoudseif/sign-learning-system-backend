package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.model.Question;
import com.signlanguage.sign_learning_system.repository.QuestionRepository;
import com.signlanguage.sign_learning_system.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<Question> getQuestionsByAssessmentId(Long assessmentId) {
        return questionRepository.findByAssessmentId(assessmentId);
    }

    @Override
    public Optional<Question> updateQuestion(Long id, Question updatedQuestion) {
        return questionRepository.findById(id).map(existing -> {
            existing.setContent(updatedQuestion.getContent());
            existing.setImageUrl(updatedQuestion.getImageUrl());
            existing.setVideoUrl(updatedQuestion.getVideoUrl());
            existing.setAssessment(updatedQuestion.getAssessment());
            return questionRepository.save(existing);
        });
    }

    @Override
    public boolean deleteQuestion(Long id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
