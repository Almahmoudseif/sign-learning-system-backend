package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.DTO.AssessmentResultResponse;
import com.signlanguage.sign_learning_system.enums.LessonLevel;
import com.signlanguage.sign_learning_system.model.Assessment;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AssessmentService {
    Assessment saveAssessment(Assessment assessment);

    List<Assessment> getAllAssessments();

    Optional<Assessment> getAssessmentById(Long id);

    Optional<Assessment> updateAssessment(Long id, Assessment assessment);

    boolean deleteAssessment(Long id);

    List<Assessment> getAssessmentsByLevel(LessonLevel level);

    List<Assessment> getAssessmentsForStudentLevel(Long studentId);

    AssessmentResultResponse evaluateAndPromoteStudent(Long studentId, Long assessmentId, Map<Long, String> answers, boolean promote);
}
