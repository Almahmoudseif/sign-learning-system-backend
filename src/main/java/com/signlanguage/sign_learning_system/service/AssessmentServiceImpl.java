package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.enums.LessonLevel;
import com.signlanguage.sign_learning_system.model.*;
import com.signlanguage.sign_learning_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AssessmentServiceImpl implements AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public Assessment saveAssessment(Assessment assessment) {
        if (assessment == null || assessment.getTitle() == null || assessment.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Assessment title is required.");
        }

        if (assessment.getQuestions() != null) {
            for (Question q : assessment.getQuestions()) {
                q.setAssessment(assessment);

                // Hakikisha answers zote zina reference kwa question
                if (q.getAnswers() != null) {
                    for (Answer a : q.getAnswers()) {
                        a.setQuestion(q);
                    }
                }
            }
        }

        return assessmentRepository.save(assessment);
    }

    @Override
    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }

    @Override
    public Optional<Assessment> getAssessmentById(Long id) {
        return assessmentRepository.findById(id);
    }

    @Override
    public Optional<Assessment> updateAssessment(Long id, Assessment updatedAssessment) {
        return assessmentRepository.findById(id).map(existing -> {
            existing.setTitle(updatedAssessment.getTitle());
            existing.setDescription(updatedAssessment.getDescription());
            existing.setLevel(updatedAssessment.getLevel());
            existing.setDate(updatedAssessment.getDate());
            return assessmentRepository.save(existing);
        });
    }

    @Override
    public boolean deleteAssessment(Long id) {
        return assessmentRepository.findById(id).map(assessment -> {
            assessmentRepository.delete(assessment);
            return true;
        }).orElse(false);
    }

    @Override
    public List<Assessment> getAssessmentsByLevel(LessonLevel level) {
        return assessmentRepository.findByLevel(level);
    }

    @Override
    public List<Assessment> getAssessmentsForStudentLevel(Long studentId) {
        Optional<User> studentOpt = userRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            return List.of();
        }

        LessonLevel level = studentOpt.get().getLevel();
        return assessmentRepository.findByLevel(level);
    }

    @Override
    public boolean evaluateAndPromoteStudent(Long studentId, Long assessmentId, Map<Long, String> answers) {
        Optional<User> studentOpt = userRepository.findById(studentId);
        Optional<Assessment> assessmentOpt = assessmentRepository.findById(assessmentId);

        if (studentOpt.isEmpty() || assessmentOpt.isEmpty()) {
            return false;
        }

        List<Question> questions = questionRepository.findByAssessmentId(assessmentId);
        if (questions.isEmpty()) {
            return false;
        }

        int total = questions.size();
        int correct = 0;

        for (Question question : questions) {
            String submittedAnswer = answers.get(question.getId());
            Optional<Answer> correctAnswer = answerRepository.findByQuestionIdAndIsCorrectTrue(question.getId());

            if (correctAnswer.isPresent() &&
                    correctAnswer.get().getContent().equalsIgnoreCase(submittedAnswer)) {
                correct++;
            }
        }

        double score = ((double) correct / total) * 100;

        if (score >= 50.0) {
            User student = studentOpt.get();
            LessonLevel currentLevel = student.getLevel();

            LessonLevel nextLevel = getNextLevel(currentLevel);
            student.setLevel(nextLevel);

            userRepository.save(student);
            return true;
        }

        return false;
    }

    private LessonLevel getNextLevel(LessonLevel currentLevel) {
        if (currentLevel == null) {
            return LessonLevel.BEGINNER;
        }
        return switch (currentLevel) {
            case BEGINNER -> LessonLevel.INTERMEDIATE;
            case INTERMEDIATE -> LessonLevel.ADVANCED;
            case ADVANCED -> LessonLevel.ADVANCED;
        };
    }
}
