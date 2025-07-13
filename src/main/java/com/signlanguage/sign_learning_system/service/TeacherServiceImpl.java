package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.model.Teacher;
import com.signlanguage.sign_learning_system.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    @Override
    public Optional<Teacher> getTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    @Override
    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    // Kipengele kipya: kuhesabu idadi ya walimu
    @Override
    public long getTeacherCount() {
        return teacherRepository.countTeachers();
    }
}
