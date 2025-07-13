package com.signlanguage.sign_learning_system.controller;

import com.signlanguage.sign_learning_system.model.Teacher;
import com.signlanguage.sign_learning_system.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teachers")
//@CrossOrigin(origins = "*")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping
    public Teacher saveTeacher(@RequestBody Teacher teacher) {
        return teacherService.saveTeacher(teacher);
    }

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public Optional<Teacher> getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }

    @GetMapping("/email/{email}")
    public Optional<Teacher> getTeacherByEmail(@PathVariable String email) {
        return teacherService.getTeacherByEmail(email);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
    }

    // 🔹 Kipande kipya cha kuhesabu idadi ya walimu
    @GetMapping("/count")
    public long getTeacherCount() {
        return teacherService.getTeacherCount();
    }
}
