package com.signlanguage.sign_learning_system.controller;

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

    @PostMapping
    public ResponseEntity<Assessment> createAssessment(@RequestBody Assessment assessment) {
        Assessment saved = assessmentService.saveAssessment(assessment);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Assessment> getAllAssessments() {
        return assessmentService.getAllAssessments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assessment> getAssessmentById(@PathVariable Long id) {
        return assessmentService.getAssessmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assessment> updateAssessment(@PathVariable Long id, @RequestBody Assessment assessment) {
        return assessmentService.updateAssessment(id, assessment)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable Long id) {
        boolean deleted = assessmentService.deleteAssessment(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/level/{level}")
    public List<Assessment> getAssessmentsByLevel(@PathVariable String level) {
        return assessmentService.getAssessmentsByLevel(level);
    }

    // ✅ Mpya: endpoint rasmi ya kuleta mitihani kwa mwanafunzi kulingana na level yake
    @GetMapping("/student/{studentId}")
    public List<Assessment> getAssessmentsForStudent(@PathVariable Long studentId) {
        return assessmentService.getAssessmentsForStudentLevel(studentId);
    }
}
