package com.signlanguage.sign_learning_system.repository;

import com.signlanguage.sign_learning_system.enums.LessonLevel;
import com.signlanguage.sign_learning_system.model.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    // Pata assessments kwa level
    List<Assessment> findByLevel(LessonLevel level);

    // Pata assessments zote za student fulani
    List<Assessment> findByStudent_Id(Long studentId);

    // Pata assessments ambazo student amezipita
    List<Assessment> findByStudent_IdAndPassedTrue(Long studentId);

    // Futa assessments zote za student fulani
    @Modifying
    @Transactional
    void deleteByStudent_Id(Long studentId);
}
