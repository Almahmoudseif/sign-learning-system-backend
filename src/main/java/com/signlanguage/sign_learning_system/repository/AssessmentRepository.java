package com.signlanguage.sign_learning_system.repository;

import com.signlanguage.sign_learning_system.model.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    List<Assessment> findByLevel(String level); // hii ndio custom query
}
