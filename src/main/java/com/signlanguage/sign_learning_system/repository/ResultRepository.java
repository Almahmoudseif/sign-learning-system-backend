package com.signlanguage.sign_learning_system.repository;

import com.signlanguage.sign_learning_system.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findByStudent_Id(Long studentId);

    List<Result> findByAssessment_Id(Long assessmentId);

    Optional<Result> findByStudent_IdAndAssessment_Id(Long studentId, Long assessmentId);

    // Hii ni method mpya: delete results zote za student
    @Modifying
    @Transactional
    @Query("DELETE FROM Result r WHERE r.student.id = :studentId")
    void deleteByStudent_Id(@Param("studentId") Long studentId);

    // 🔹 Mpya: kupata matokeo yote ya wanafunzi kwa mwalimu
    List<Result> findByAssessment_Teacher_Id(Long teacherId);
}
