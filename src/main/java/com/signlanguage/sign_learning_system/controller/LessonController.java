package com.signlanguage.sign_learning_system.controller;

import com.signlanguage.sign_learning_system.enums.LessonLevel;
import com.signlanguage.sign_learning_system.model.Lesson;
import com.signlanguage.sign_learning_system.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lessons")
@CrossOrigin(origins = "http://localhost:3000")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping
    public List<Lesson> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/{id}")
    public Optional<Lesson> getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id);
    }

    @GetMapping("/level/{level}")
    public List<Lesson> getLessonsByLevel(@PathVariable LessonLevel level) {
        return lessonService.getLessonsByLevel(level);
    }

    @PostMapping
    public Lesson createLesson(@RequestBody Lesson lesson) {
        return lessonService.saveLesson(lesson);
    }

    @DeleteMapping("/{id}")
    public void deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
    }
}
