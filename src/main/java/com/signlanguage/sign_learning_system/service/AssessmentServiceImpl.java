package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.model.Assessment;
import com.signlanguage.sign_learning_system.repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentRepository assessmentRepository;

    @Autowired
    public AssessmentServiceImpl(AssessmentRepository assessmentRepository) {
        this.assessmentRepository = assessmentRepository;
    }

    @Override
    public Assessment saveAssessment(Assessment assessment) {
        return assessmentRepository.save(assessment);
    }

    @Override
    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }

    @Override
    public Optional<Assessment> getAssessmentById(Long id) {
        return assessmentRepository.findById(id);
    }

    @Override
    public Optional<Assessment> updateAssessment(Long id, Assessment updatedAssessment) {
        return assessmentRepository.findById(id).map(existing -> {
            existing.setTitle(updatedAssessment.getTitle());
            existing.setDescription(updatedAssessment.getDescription());
            existing.setLevel(updatedAssessment.getLevel());
            existing.setDate(updatedAssessment.getDate());
            return assessmentRepository.save(existing);
        });
    }

    @Override
    public boolean deleteAssessment(Long id) {
        if (assessmentRepository.existsById(id)) {
            assessmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Assessment> getAssessmentsByLevel(String level) {
        return assessmentRepository.findByLevel(level);
    }
}
