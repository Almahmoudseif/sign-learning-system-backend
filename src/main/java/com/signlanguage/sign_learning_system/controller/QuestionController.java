package com.signlanguage.sign_learning_system.controller;

import com.signlanguage.sign_learning_system.model.Question;
import com.signlanguage.sign_learning_system.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // ✅ Add new question
    @PostMapping
    public Question createQuestion(@RequestBody Question question) {
        return questionService.saveQuestion(question);
    }

    // ✅ Get all questions
    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    // ✅ Get question by ID
    @GetMapping("/{id}")
    public Question getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
    }

    // ✅ Get all questions by assessment ID
    @GetMapping("/assessment/{assessmentId}")
    public List<Question> getQuestionsByAssessment(@PathVariable Long assessmentId) {
        return questionService.getQuestionsByAssessmentId(assessmentId);
    }

    // ✅ Delete question by ID
    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return "Question deleted successfully.";
    }
}
