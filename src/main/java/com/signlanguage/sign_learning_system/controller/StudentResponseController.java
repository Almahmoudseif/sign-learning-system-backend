package com.signlanguage.sign_learning_system.controller;

import com.signlanguage.sign_learning_system.model.StudentResponse;
import com.signlanguage.sign_learning_system.service.StudentResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-responses")
public class StudentResponseController {

    @Autowired
    private StudentResponseService studentResponseService;

    @PostMapping
    public StudentResponse createResponse(@RequestBody StudentResponse response) {
        return studentResponseService.saveResponse(response);
    }

    @GetMapping
    public List<StudentResponse> getAllResponses() {
        return studentResponseService.getAllResponses();
    }

    @GetMapping("/{id}")
    public StudentResponse getResponseById(@PathVariable Long id) {
        return studentResponseService.getResponseById(id)
                .orElseThrow(() -> new RuntimeException("StudentResponse not found with id: " + id));
    }

    @GetMapping("/student/{studentId}")
    public List<StudentResponse> getResponsesByStudent(@PathVariable Long studentId) {
        return studentResponseService.getResponsesByStudentId(studentId);
    }

    @GetMapping("/question/{questionId}")
    public List<StudentResponse> getResponsesByQuestion(@PathVariable Long questionId) {
        return studentResponseService.getResponsesByQuestionId(questionId);
    }

    @GetMapping("/student/{studentId}/question/{questionId}")
    public List<StudentResponse> getResponsesByStudentAndQuestion(@PathVariable Long studentId, @PathVariable Long questionId) {
        return studentResponseService.getResponsesByStudentAndQuestion(studentId, questionId);
    }

    @DeleteMapping("/{id}")
    public String deleteResponse(@PathVariable Long id) {
        studentResponseService.deleteResponse(id);
        return "StudentResponse with id " + id + " deleted successfully.";
    }
}
