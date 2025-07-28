package com.signlanguage.sign_learning_system.controller;

import com.signlanguage.sign_learning_system.enums.LessonLevel;
import com.signlanguage.sign_learning_system.model.Lesson;
import com.signlanguage.sign_learning_system.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @Value("${upload.path:uploads/}")
    private String uploadDir;

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

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<Lesson> uploadLesson(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("level") LessonLevel level,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) uploadPath.mkdirs();

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File destinationFile = new File(uploadPath, filename);
            file.transferTo(destinationFile);

            String videoUrl = "/uploads/" + filename;

            Lesson lesson = new Lesson();
            lesson.setTitle(title);
            lesson.setDescription(description);
            lesson.setLevel(level);
            lesson.setVideoPath(videoUrl);

            Lesson savedLesson = lessonService.saveLesson(lesson);
            return new ResponseEntity<>(savedLesson, HttpStatus.CREATED);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload video", e);
        }
    }

    @PostMapping("/upload/image")
    public ResponseEntity<?> uploadLessonImage(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("level") LessonLevel level,
            @RequestParam("file") MultipartFile file) throws IOException {

        String imageDir = "uploads/images/";
        File dir = new File(imageDir);
        if (!dir.exists()) dir.mkdirs();

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filepath = Paths.get(imageDir + filename);
        Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);

        Lesson lesson = new Lesson();
        lesson.setTitle(title);
        lesson.setDescription(description);
        lesson.setLevel(level);
        lesson.setImageUrl("/uploads/images/" + filename);

        lessonService.saveLesson(lesson);
        return ResponseEntity.ok("Lesson image uploaded successfully");
    }

    @PutMapping(value = "/update/image/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateLessonImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            Lesson lesson = lessonService.getLessonById(id)
                    .orElseThrow(() -> new RuntimeException("Lesson not found with ID: " + id));

            String uploadDir = "uploads/images/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filepath = Paths.get(uploadDir + filename);
            Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);

            lesson.setImageUrl("/uploads/images/" + filename);
            lessonService.saveLesson(lesson); // ✅ Important!

            return ResponseEntity.ok("Image updated successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update image");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
    }
}
