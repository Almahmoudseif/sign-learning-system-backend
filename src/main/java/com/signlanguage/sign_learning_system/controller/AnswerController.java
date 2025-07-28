package com.signlanguage.sign_learning_system.controller;

import com.signlanguage.sign_learning_system.model.Answer;
import com.signlanguage.sign_learning_system.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping
    public Answer createAnswer(@RequestBody Answer answer) {
        return answerService.saveAnswer(answer);
    }

    @GetMapping
    public List<Answer> getAllAnswers() {
        return answerService.getAllAnswers();
    }

    @GetMapping("/{id}")
    public Answer getAnswerById(@PathVariable Long id) {
        return answerService.getAnswerById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found with id: " + id));
    }

    @GetMapping("/question/{questionId}")
    public List<Answer> getAnswersByQuestionId(@PathVariable Long questionId) {
        return answerService.getAnswersByQuestionId(questionId);
    }

    @DeleteMapping("/{id}")
    public String deleteAnswer(@PathVariable Long id) {
        answerService.deleteAnswer(id);
        return "Answer with id " + id + " deleted successfully.";
    }
}
