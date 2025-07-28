package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.model.Result;
import com.signlanguage.sign_learning_system.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    public Result saveResult(Result result) {
        return resultRepository.save(result);
    }

    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    public Optional<Result> getResultById(Long id) {
        return resultRepository.findById(id);
    }

    public List<Result> getResultsByStudentId(Long studentId) {
        return resultRepository.findByStudentId(studentId);
    }

    public List<Result> getResultsByAssessmentId(Long assessmentId) {
        return resultRepository.findByAssessmentId(assessmentId);
    }

    public Optional<Result> getResultByStudentAndAssessment(Long studentId, Long assessmentId) {
        return resultRepository.findByStudentIdAndAssessmentId(studentId, assessmentId);
    }

    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }
}
