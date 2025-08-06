package com.signlanguage.sign_learning_system.controller;

import com.signlanguage.sign_learning_system.DTO.QuestionRequest;
import com.signlanguage.sign_learning_system.model.Assessment;
import com.signlanguage.sign_learning_system.model.Answer;
import com.signlanguage.sign_learning_system.model.Question;
import com.signlanguage.sign_learning_system.repository.AnswerRepository;
import com.signlanguage.sign_learning_system.repository.AssessmentRepository;
import com.signlanguage.sign_learning_system.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final AssessmentRepository assessmentRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public QuestionController(
            QuestionService questionService,
            AssessmentRepository assessmentRepository,
            AnswerRepository answerRepository
    ) {
        this.questionService = questionService;
        this.assessmentRepository = assessmentRepository;
        this.answerRepository = answerRepository;
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody QuestionRequest request) {
        Assessment assessment = assessmentRepository.findById(request.getAssessmentId())
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        Question question = new Question();
        question.setContent(request.getContent());
        question.setImageUrl(request.getImageUrl());
        question.setVideoUrl(request.getVideoUrl());
        question.setAssessment(assessment);

        // Save question kwanza
        Question savedQuestion = questionService.saveQuestion(question);

        // Save answers
        List<Answer> answerList = new ArrayList<>();
        if (request.getAnswers() != null) {
            for (QuestionRequest.AnswerRequest answerReq : request.getAnswers()) {
                Answer answer = new Answer();
                answer.setContent(answerReq.getContent());
                answer.setCorrect(answerReq.isCorrect());
                answer.setQuestion(savedQuestion);
                answerRepository.save(answer);
                answerList.add(answer);
            }
        }

        // Set answers kwenye question na return
        savedQuestion.setAnswers(answerList);

        return ResponseEntity.ok(savedQuestion);
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/assessment/{assessmentId}")
    public ResponseEntity<List<Question>> getQuestionsByAssessment(@PathVariable Long assessmentId) {
        return ResponseEntity.ok(questionService.getQuestionsByAssessmentId(assessmentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        return questionService.updateQuestion(id, question)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        if (questionService.deleteQuestion(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
