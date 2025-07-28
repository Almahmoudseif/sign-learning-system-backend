package com.signlanguage.sign_learning_system.controller;

import com.signlanguage.sign_learning_system.DTO.QuestionRequest;
import com.signlanguage.sign_learning_system.model.Assessment;
import com.signlanguage.sign_learning_system.model.Question;
import com.signlanguage.sign_learning_system.model.Answer;
import com.signlanguage.sign_learning_system.repository.AssessmentRepository;
import com.signlanguage.sign_learning_system.service.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AssessmentRepository assessmentRepository;

    // ✅ Create question with answers from DTO
    @PostMapping
    public Question createQuestion(@RequestBody QuestionRequest questionRequest) {
        Assessment assessment = assessmentRepository.findById(questionRequest.getAssessmentId())
                .orElseThrow(() -> new RuntimeException("Assessment not found with id: " + questionRequest.getAssessmentId()));

        Question question = new Question();
        question.setContent(questionRequest.getContent());
        question.setImageUrl(questionRequest.getImageUrl());
        question.setVideoUrl(questionRequest.getVideoUrl());
        question.setAssessment(assessment);

        // Handle answers
        List<Answer> answers = new ArrayList<>();
        if (questionRequest.getAnswers() != null) {
            for (var answerRequest : questionRequest.getAnswers()) {
                Answer answer = new Answer();
                answer.setContent(answerRequest.getContent());
                answer.setCorrect(answerRequest.isCorrect());
                answer.setQuestion(question);
                answers.add(answer);
            }
        }

        question.setAnswers(answers);

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

    // ✅ Get questions by assessment ID
    @GetMapping("/assessment/{assessmentId}")
    public List<Question> getQuestionsByAssessment(@PathVariable Long assessmentId) {
        return questionService.getQuestionsByAssessmentId(assessmentId);
    }

    // ✅ Delete question
    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return "Question deleted successfully.";
    }
}
