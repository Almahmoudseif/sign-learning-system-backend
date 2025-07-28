package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.model.Assessment;
import com.signlanguage.sign_learning_system.model.User;
import com.signlanguage.sign_learning_system.repository.AssessmentRepository;
import com.signlanguage.sign_learning_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssessmentServiceImpl implements AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private UserRepository userRepository;

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
    public Optional<Assessment> updateAssessment(Long id, Assessment assessment) {
        return assessmentRepository.findById(id).map(existing -> {
            existing.setTitle(assessment.getTitle());
            existing.setDescription(assessment.getDescription());
            existing.setLevel(assessment.getLevel());
            existing.setDate(assessment.getDate());
            return assessmentRepository.save(existing);
        });
    }

    @Override
    public boolean deleteAssessment(Long id) {
        return assessmentRepository.findById(id).map(assessment -> {
            assessmentRepository.delete(assessment);
            return true;
        }).orElse(false);
    }

    @Override
    public List<Assessment> getAssessmentsByLevel(String level) {
        return assessmentRepository.findByLevel(level);
    }

    @Override
    public List<Assessment> getAssessmentsForStudentLevel(Long studentId) {
        Optional<User> userOpt = userRepository.findById(studentId);
        if (userOpt.isEmpty()) {
            return List.of(); // empty list
        }

        String level = userOpt.get().getLevel();
        return assessmentRepository.findByLevel(level);
    }
}
