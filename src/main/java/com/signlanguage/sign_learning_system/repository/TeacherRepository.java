package com.signlanguage.sign_learning_system.repository;

import com.signlanguage.sign_learning_system.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    // Kuangalia kama teacher yupo kwa email
    Optional<Teacher> findByEmail(String email);

    // Kuleta idadi ya walimu
    @Query("SELECT COUNT(t) FROM Teacher t")
    long countTeachers();
}
