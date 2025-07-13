package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.model.StudentResponse;
import com.signlanguage.sign_learning_system.repository.StudentResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentResponseService {

    @Autowired
    private StudentResponseRepository studentResponseRepository;

    public StudentResponse saveResponse(StudentResponse response) {
        return studentResponseRepository.save(response);
    }

    public List<StudentResponse> getAllResponses() {
        return studentResponseRepository.findAll();
    }

    public Optional<StudentResponse> getResponseById(Long id) {
        return studentResponseRepository.findById(id);
    }

    public List<StudentResponse> getResponsesByStudentId(Long studentId) {
        return studentResponseRepository.findByStudentId(studentId);
    }

    public List<StudentResponse> getResponsesByQuestionId(Long questionId) {
        return studentResponseRepository.findByQuestionId(questionId);
    }

    public List<StudentResponse> getResponsesByStudentAndQuestion(Long studentId, Long questionId) {
        return studentResponseRepository.findByStudentIdAndQuestionId(studentId, questionId);
    }

    public void deleteResponse(Long id) {
        studentResponseRepository.deleteById(id);
    }
}
