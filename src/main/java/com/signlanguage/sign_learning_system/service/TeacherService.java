package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.model.Teacher;
import java.util.List;
import java.util.Optional;

public interface TeacherService {

    List<Teacher> getAllTeachers();

    Optional<Teacher> getTeacherById(Long id);
 
    Optional<Teacher> getTeacherByEmail(String email);

    Teacher saveTeacher(Teacher teacher);

    void deleteTeacher(Long id);

    // Kipengele kipya cha kuhesabu idadi ya walimu
    long getTeacherCount();
}
