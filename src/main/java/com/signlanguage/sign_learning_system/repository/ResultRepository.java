package com.signlanguage.sign_learning_system.repository;

import com.signlanguage.sign_learning_system.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findByStudentId(Long studentId);

    List<Result> findByAssessmentId(Long assessmentId);

    List<Result> findByStudentIdAndAssessmentId(Long studentId, Long assessmentId);
}
