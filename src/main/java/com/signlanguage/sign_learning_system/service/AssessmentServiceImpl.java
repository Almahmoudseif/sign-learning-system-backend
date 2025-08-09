package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.DTO.AssessmentResultResponse;
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
    public AssessmentResultResponse evaluateAndPromoteStudent(Long studentId, Long assessmentId, Map<Long, String> answers, boolean promote) {
        Optional<User> studentOpt = userRepository.findById(studentId);
        Optional<Assessment> assessmentOpt = assessmentRepository.findById(assessmentId);

        if (studentOpt.isEmpty() || assessmentOpt.isEmpty()) {
            return new AssessmentResultResponse("Assessment au mwanafunzi hakupatikana", 0, false);
        }

        List<Question> questions = questionRepository.findByAssessmentId(assessmentId);
        if (questions.isEmpty()) {
            return new AssessmentResultResponse("Hakuna maswali kwenye assessment hii", 0, false);
        }

        // Hakikisha kila question ina jibu moja tu sahihi
        for (Question q : questions) {
            long correctCount = q.getAnswers().stream().filter(Answer::isCorrect).count();
            if (correctCount != 1) {
                return new AssessmentResultResponse("Swali '" + q.getContent() + "' linapaswa kuwa na jibu moja tu sahihi.", 0, false);
            }
        }

        int totalQuestions = questions.size();
        int correctAnswers = 0;

        for (Question question : questions) {
            String submittedAnswerIdStr = answers.get(question.getId());
            if (submittedAnswerIdStr == null) {
                return new AssessmentResultResponse("Tafadhali jibu swali: " + question.getContent(), (int) (((double) correctAnswers / totalQuestions) * 100), false);
            }
            Long submittedAnswerId;
            try {
                submittedAnswerId = Long.parseLong(submittedAnswerIdStr);
            } catch (NumberFormatException e) {
                return new AssessmentResultResponse("Jibu la swali '" + question.getContent() + "' halieleweki", (int) (((double) correctAnswers / totalQuestions) * 100), false);
            }

            Optional<Answer> correctAnswerOpt = answerRepository.findByQuestionIdAndIsCorrectTrue(question.getId());

            if (correctAnswerOpt.isEmpty()) {
                return new AssessmentResultResponse("Hakuna jibu sahihi limewekwa kwa swali: " + question.getContent(), (int) (((double) correctAnswers / totalQuestions) * 100), false);
            }

            if (correctAnswerOpt.get().getId().equals(submittedAnswerId)) {
                correctAnswers++;
            } else {
                return new AssessmentResultResponse(
                        "Samahani, hujafaulu. Jaribu tena.",
                        (int) (((double) correctAnswers / totalQuestions) * 100),
                        false
                );
            }
        }

        // Ikiwa umefika hapa, maana majibu yote ni sahihi
        double scorePercentage = 100;
        boolean passed = true;

        if (passed && promote) {
            promoteStudentToNextLevel(studentOpt.get());
        }

        return new AssessmentResultResponse(
                "Hongera! Umeshinda na umehamishiwa daraja jingine.",
                (int) scorePercentage,
                true
        );
    }

    private void promoteStudentToNextLevel(User student) {
        LessonLevel currentLevel = student.getLevel();
        LessonLevel nextLevel = getNextLevel(currentLevel);
        student.setLevel(nextLevel);
        userRepository.save(student);
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
