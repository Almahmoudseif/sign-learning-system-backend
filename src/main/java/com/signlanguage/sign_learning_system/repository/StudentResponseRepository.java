package com.signlanguage.sign_learning_system.repository;

import com.signlanguage.sign_learning_system.model.StudentResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentResponseRepository extends JpaRepository<StudentResponse, Long> {

    List<StudentResponse> findByStudentId(Long studentId);

    List<StudentResponse> findByQuestionId(Long questionId);

    List<StudentResponse> findByStudentIdAndQuestionId(Long studentId, Long questionId);
}
