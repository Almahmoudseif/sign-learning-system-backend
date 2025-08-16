package com.signlanguage.sign_learning_system.controller;

import com.signlanguage.sign_learning_system.enums.LessonLevel;
import com.signlanguage.sign_learning_system.model.Lesson;
import com.signlanguage.sign_learning_system.model.User;
import com.signlanguage.sign_learning_system.service.LessonService;
import com.signlanguage.sign_learning_system.service.UserService;
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

    @Autowired
    private UserService userService;

    @Value("${upload.path}")
    private String uploadPath;

    private final String BASE_IMAGE_URL = "http://192.168.43.33:8080/uploads/images/";
    private final String BASE_VIDEO_URL = "http://192.168.43.33:8080/uploads/videos/";

    @GetMapping
    public List<Lesson> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) {
        Lesson lesson = lessonService.getLessonById(id);
        return lesson != null ? ResponseEntity.ok(lesson) : ResponseEntity.notFound().build();
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Lesson>> getLessonsByTeacher(@PathVariable Long teacherId) {
        List<Lesson> lessons = lessonService.getLessonsByTeacherId(teacherId);
        return lessons.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lessons);
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<List<Lesson>> getLessonsByLevel(@PathVariable String level) {
        LessonLevel lessonLevel;
        try {
            lessonLevel = LessonLevel.fromString(level);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        List<Lesson> lessons = lessonService.getLessonsByLevel(lessonLevel);
        return lessons.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lessons);
    }

    @GetMapping("/level/{level}/teacher/{teacherId}")
    public ResponseEntity<List<Lesson>> getLessonsByLevelAndTeacherId(@PathVariable String level, @PathVariable Long teacherId) {
        LessonLevel lessonLevel;
        try {
            lessonLevel = LessonLevel.fromString(level);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        List<Lesson> lessons = lessonService.getLessonsByLevelAndTeacherId(lessonLevel, teacherId);
        return lessons.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lessons);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Lesson>> getLessonsByStudent(@PathVariable Long studentId, @RequestParam(required = false) String level) {
        User student = userService.getUserById(studentId);
        if (student == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        LessonLevel targetLevel;
        if (level != null) {
            try {
                targetLevel = LessonLevel.fromString(level);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            targetLevel = student.getLevel();
        }

        List<Lesson> lessons = lessonService.getLessonsByLevel(targetLevel);
        return lessons.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lessons);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable Long id) {
        return lessonService.deleteLesson(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found");
    }

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

    @GetMapping("/student/{studentId}/passed")
    public ResponseEntity<List<Lesson>> getPassedLessons(@PathVariable Long studentId) {
        List<Lesson> lessons = lessonService.getPassedLessonsByStudent(studentId);
        return lessons.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lessons);
    }

    @PostMapping
    public ResponseEntity<Lesson> createLesson(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("level") String levelStr,
            @RequestParam(value = "teacherId") Long teacherId,
            @RequestParam(value = "video", required = false) MultipartFile videoFile,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) {
        LessonLevel level;
        try {
            level = LessonLevel.fromString(levelStr);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

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

    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("lessonType") String lessonType,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            @RequestParam(value = "video", required = false) MultipartFile videoFile
    ) {
        Lesson lesson = lessonService.getLessonById(id);
        if (lesson == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        lesson.setTitle(title);
        lesson.setDescription(description);
        lesson.setLessonType(lessonType);

        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                File imageDest = new File(uploadPath + File.separator + "images", imageFileName);
                imageDest.getParentFile().mkdirs();
                imageFile.transferTo(imageDest);
                lesson.setImageUrl(BASE_IMAGE_URL + imageFileName);
                lesson.setVideoUrl(null);
            }

            if (videoFile != null && !videoFile.isEmpty()) {
                String videoFileName = UUID.randomUUID() + "_" + videoFile.getOriginalFilename();
                File videoDest = new File(uploadPath + File.separator + "videos", videoFileName);
                videoDest.getParentFile().mkdirs();
                videoFile.transferTo(videoDest);
                lesson.setVideoUrl(BASE_VIDEO_URL + videoFileName);
                lesson.setImageUrl(null);
            }

            Lesson updatedLesson = lessonService.saveLesson(lesson);
            return ResponseEntity.ok(updatedLesson);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
