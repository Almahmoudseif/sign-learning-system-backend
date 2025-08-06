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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @Value("${upload.path}")
    private String uploadPath;

    private final String BASE_IMAGE_URL = "http://192.168.43.33:8080/uploads/images/";
    private final String BASE_VIDEO_URL = "http://192.168.43.33:8080/uploads/videos/";

    // 1. Get all lessons
    @GetMapping
    public List<Lesson> getAllLessons() {
        return lessonService.getAllLessons();
    }

    // 2. Get lesson by ID
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) {
        Lesson lesson = lessonService.getLessonById(id);
        return lesson != null ? ResponseEntity.ok(lesson) : ResponseEntity.notFound().build();
    }

    // 3. Get all lessons by teacherId
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Lesson>> getLessonsByTeacher(@PathVariable Long teacherId) {
        List<Lesson> lessons = lessonService.getLessonsByTeacherId(teacherId);
        return lessons.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lessons);
    }

    // 4. Get lessons by level
    @GetMapping("/level/{level}")
    public ResponseEntity<List<Lesson>> getLessonsByLevel(@PathVariable LessonLevel level) {
        List<Lesson> lessons = lessonService.getLessonsByLevel(level);
        return lessons.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lessons);
    }

    // 5. Get lessons by level and teacherId
    @GetMapping("/level/{level}/teacher/{teacherId}")
    public ResponseEntity<List<Lesson>> getLessonsByLevelAndTeacherId(@PathVariable LessonLevel level, @PathVariable Long teacherId) {
        List<Lesson> lessons = lessonService.getLessonsByLevelAndTeacherId(level, teacherId);
        return lessons.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lessons);
    }

    // 6. Delete lesson by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable Long id) {
        return lessonService.deleteLesson(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found");
    }

    // 7. Update lesson image
    @PutMapping("/{id}/image")
    public ResponseEntity<Lesson> updateLessonImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File imageDest = new File(uploadPath + File.separator + "images", fileName);
            imageDest.getParentFile().mkdirs();
            file.transferTo(imageDest);

            Lesson updatedLesson = lessonService.updateLessonImage(id, BASE_IMAGE_URL + fileName);
            return updatedLesson != null ? ResponseEntity.ok(updatedLesson) : ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 8. Create new lesson with optional image & video
    @PostMapping
    public ResponseEntity<Lesson> createLesson(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("level") LessonLevel level,
            @RequestParam(value = "teacherId") Long teacherId,
            @RequestParam(value = "video", required = false) MultipartFile videoFile,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) {
        try {
            String videoFileName = null;
            String imageFileName = null;

            if (videoFile != null && !videoFile.isEmpty()) {
                videoFileName = UUID.randomUUID() + "_" + videoFile.getOriginalFilename();
                File videoDest = new File(uploadPath + File.separator + "videos", videoFileName);
                videoDest.getParentFile().mkdirs();
                videoFile.transferTo(videoDest);
            }

            if (imageFile != null && !imageFile.isEmpty()) {
                imageFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                File imageDest = new File(uploadPath + File.separator + "images", imageFileName);
                imageDest.getParentFile().mkdirs();
                imageFile.transferTo(imageDest);
            }

            Lesson lesson = new Lesson();
            lesson.setTitle(title);
            lesson.setDescription(description);
            lesson.setLevel(level);
            lesson.setTeacherId(teacherId);
            lesson.setVideoUrl(videoFileName != null ? BASE_VIDEO_URL + videoFileName : null);
            lesson.setImageUrl(imageFileName != null ? BASE_IMAGE_URL + imageFileName : null);

            Lesson savedLesson = lessonService.saveLesson(lesson);
            return new ResponseEntity<>(savedLesson, HttpStatus.CREATED);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
