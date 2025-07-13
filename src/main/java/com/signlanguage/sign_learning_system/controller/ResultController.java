package com.signlanguage.sign_learning_system.controller;

import com.signlanguage.sign_learning_system.model.Result;
import com.signlanguage.sign_learning_system.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
@CrossOrigin(origins = "*")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @PostMapping
    public Result createResult(@RequestBody Result result) {
        return resultService.saveResult(result);
    }

    @GetMapping
    public List<Result> getAllResults() {
        return resultService.getAllResults();
    }

    @GetMapping("/{id}")
    public Result getResultById(@PathVariable Long id) {
        return resultService.getResultById(id)
                .orElseThrow(() -> new RuntimeException("Result not found with id: " + id));
    }

    @GetMapping("/student/{studentId}")
    public List<Result> getResultsByStudent(@PathVariable Long studentId) {
        return resultService.getResultsByStudentId(studentId);
    }

    @GetMapping("/assessment/{assessmentId}")
    public List<Result> getResultsByAssessment(@PathVariable Long assessmentId) {
        return resultService.getResultsByAssessmentId(assessmentId);
    }

    @GetMapping("/student/{studentId}/assessment/{assessmentId}")
    public Result getResultByStudentAndAssessment(@PathVariable Long studentId, @PathVariable Long assessmentId) {
        return resultService.getResultByStudentAndAssessment(studentId, assessmentId)
                .orElseThrow(() -> new RuntimeException("Result not found for student " + studentId + " and assessment " + assessmentId));
    }

    @DeleteMapping("/{id}")
    public String deleteResult(@PathVariable Long id) {
        resultService.deleteResult(id);
        return "Result with id " + id + " deleted successfully.";
    }
}
