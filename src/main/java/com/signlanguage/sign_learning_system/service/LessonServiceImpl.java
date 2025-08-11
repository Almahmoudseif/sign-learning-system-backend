package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.enums.LessonLevel;
import com.signlanguage.sign_learning_system.model.Assessment;
import com.signlanguage.sign_learning_system.model.Lesson;
import com.signlanguage.sign_learning_system.repository.AssessmentRepository;
import com.signlanguage.sign_learning_system.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Override
    public Lesson saveLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id).orElse(null);
    }

    @Override
    public List<Lesson> getLessonsByLevel(LessonLevel level) {
        return lessonRepository.findByLevel(level);
    }

    @Override
    public boolean deleteLesson(Long id) {
        Optional<Lesson> lessonOpt = lessonRepository.findById(id);
        if (lessonOpt.isPresent()) {
            lessonRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Lesson updateLessonImage(Long id, String imageUrl) {
        return lessonRepository.findById(id)
                .map(lesson -> {
                    lesson.setImageUrl(imageUrl);
                    return lessonRepository.save(lesson);
                }).orElse(null);
    }

    @Override
    public List<Lesson> getVideoLessonsOnly() {
        return lessonRepository.findByVideoUrlIsNotNullAndImageUrlIsNull();
    }

    @Override
    public List<Lesson> findByLevel(LessonLevel level) {
        return lessonRepository.findByLevel(level);
    }

    @Override
    public List<Lesson> getLessonsByTeacherId(Long teacherId) {
        return lessonRepository.findByTeacherId(teacherId);
    }

    @Override
    public List<Lesson> getLessonsByLevelAndTeacherId(LessonLevel level, Long teacherId) {
        return lessonRepository.findByLevelAndTeacherId(level, teacherId);
    }

    @Override
    public List<Lesson> getPassedLessonsByStudent(Long studentId) {
        List<Assessment> passedAssessments = assessmentRepository.findByStudent_IdAndPassedTrue(studentId);
        if (passedAssessments.isEmpty()) {
            return List.of();
        }
        List<Long> lessonIds = passedAssessments.stream()
                .map(a -> a.getLesson().getId()) // Badala ya getLessonId()
                .distinct()
                .toList();
        return lessonRepository.findAllById(lessonIds);
    }

}
