package com.signlanguage.sign_learning_system.controller;

import com.signlanguage.sign_learning_system.DTO.AssessmentSubmissionRequest;
import com.signlanguage.sign_learning_system.enums.LessonLevel;
import com.signlanguage.sign_learning_system.model.Assessment;
import com.signlanguage.sign_learning_system.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    // Create a new assessment
    @PostMapping
    public ResponseEntity<?> createAssessment(@RequestBody Assessment assessment) {
        try {
            Assessment saved = assessmentService.saveAssessment(assessment);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid assessment data: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

    // Get all assessments
    @GetMapping
    public List<Assessment> getAllAssessments() {
        return assessmentService.getAllAssessments();  // Hii inatakiwa iwepo kwenye service
    }

    // Get a single assessment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Assessment> getAssessmentById(@PathVariable Long id) {
        return assessmentService.getAssessmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update an existing assessment
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssessment(@PathVariable Long id, @RequestBody Assessment assessment) {
        try {
            return assessmentService.updateAssessment(id, assessment)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

    // Delete an assessment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable Long id) {
        boolean deleted = assessmentService.deleteAssessment(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get assessments by level (BEGINNER, INTERMEDIATE, ADVANCED)
    @GetMapping("/level/{level}")
    public List<Assessment> getAssessmentsByLevel(@PathVariable String level) {
        try {
            LessonLevel lessonLevel = LessonLevel.valueOf(level.toUpperCase());
            return assessmentService.getAssessmentsByLevel(lessonLevel);
        } catch (IllegalArgumentException e) {
            return List.of(); // return empty list if level invalid
        }
    }

    // Get assessments based on student's level
    @GetMapping("/student/{studentId}")
    public List<Assessment> getAssessmentsForStudent(@PathVariable Long studentId) {
        return assessmentService.getAssessmentsForStudentLevel(studentId);
    }

    // Submit assessment answers and auto-evaluate
    @PostMapping("/submit")
    public ResponseEntity<String> submitAssessment(@RequestBody AssessmentSubmissionRequest submissionRequest) {
        boolean passed = assessmentService.evaluateAndPromoteStudent(
                submissionRequest.getStudentId(),
                submissionRequest.getAssessmentId(),
                submissionRequest.getAnswers()
        );

        if (passed) {
            return ResponseEntity.ok("Umefaulu! Umehamishiwa level inayofuata.");
        } else {
            return ResponseEntity.ok("Samahani, hujafaulu. Jaribu tena.");
        }
    }
}
