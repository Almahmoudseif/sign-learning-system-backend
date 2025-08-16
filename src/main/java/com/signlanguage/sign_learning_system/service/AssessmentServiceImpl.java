package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.DTO.AssessmentResultResponse;
import com.signlanguage.sign_learning_system.enums.LessonLevel;
import com.signlanguage.sign_learning_system.model.*;
import com.signlanguage.sign_learning_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    private ResultRepository resultRepository; // ✅ Mpya: kwa ajili ya kuhifadhi matokeo

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

    // ✅ Version mpya ya method (Map<Long, Long>)
    @Override
    public AssessmentResultResponse evaluateAndPromoteStudent(Long studentId, Long assessmentId, Map<Long, Long> answers, boolean promoteIfPass) {
        Optional<User> studentOpt = userRepository.findById(studentId);
        Optional<Assessment> assessmentOpt = assessmentRepository.findById(assessmentId);

        if (studentOpt.isEmpty() || assessmentOpt.isEmpty()) {
            return new AssessmentResultResponse("Assessment au mwanafunzi hakupatikana", 0, false);
        }

        User student = studentOpt.get();
        Assessment assessment = assessmentOpt.get();

        List<Question> questions = questionRepository.findByAssessmentId(assessmentId);
        if (questions.isEmpty()) {
            return new AssessmentResultResponse("Hakuna maswali kwenye assessment hii", 0, false);
        }

        int totalQuestions = questions.size();
        int correctAnswers = 0;

        for (Question question : questions) {
            Long submittedAnswerId = answers.get(question.getId());
            if (submittedAnswerId == null) continue; // Swali halikujibiwa

            Optional<Answer> correctAnswerOpt = answerRepository.findByQuestionIdAndIsCorrectTrue(question.getId());
            if (correctAnswerOpt.isPresent() && correctAnswerOpt.get().getId().equals(submittedAnswerId)) {
                correctAnswers++;
            }
        }

        int scorePercentage = (int) Math.round(((double) correctAnswers / totalQuestions) * 100);
        boolean passed = scorePercentage >= 50;

        String grade;
        if (scorePercentage >= 80) grade = "A";
        else if (scorePercentage >= 65) grade = "B";
        else if (scorePercentage >= 50) grade = "C";
        else grade = "F";

        Result result = resultRepository.findByStudent_IdAndAssessment_Id(studentId, assessmentId)
                .orElse(new Result());
        result.setStudent(student);
        result.setAssessment(assessment);
        result.setScore(scorePercentage);
        result.setGrade(grade);
        result.setSubmittedAt(LocalDateTime.now());
        resultRepository.save(result);

        if (passed && promoteIfPass) promoteStudentToNextLevel(student);

        String message = passed ? "Hongera! Umefaulu." : "Samahani, hujafaulu. Jaribu tena.";
        return new AssessmentResultResponse(message, scorePercentage, passed);
    }

    // ✅ Version ya legacy (Map<Long, String>)
    @Override
    public AssessmentResultResponse evaluateAndPromoteStudentLegacy(Long studentId, Long assessmentId, Map<Long, String> answers, boolean promote) {
        Optional<User> studentOpt = userRepository.findById(studentId);
        Optional<Assessment> assessmentOpt = assessmentRepository.findById(assessmentId);

        if (studentOpt.isEmpty() || assessmentOpt.isEmpty()) {
            return new AssessmentResultResponse("Assessment au mwanafunzi hakupatikana", 0, false);
        }

        User student = studentOpt.get();
        Assessment assessment = assessmentOpt.get();
        List<Question> questions = questionRepository.findByAssessmentId(assessmentId);

        if (questions.isEmpty()) {
            return new AssessmentResultResponse("Hakuna maswali kwenye assessment hii", 0, false);
        }

        int totalQuestions = questions.size();
        int correctAnswers = 0;

        for (Question question : questions) {
            String submittedAnswerStr = answers.get(question.getId());
            if (submittedAnswerStr == null) continue;

            Long submittedAnswerId;
            try {
                submittedAnswerId = Long.parseLong(submittedAnswerStr);
            } catch (NumberFormatException e) {
                continue;
            }

            Optional<Answer> correctAnswerOpt = answerRepository.findByQuestionIdAndIsCorrectTrue(question.getId());
            if (correctAnswerOpt.isPresent() && correctAnswerOpt.get().getId().equals(submittedAnswerId)) {
                correctAnswers++;
            }
        }

        int scorePercentage = (int) Math.round(((double) correctAnswers / totalQuestions) * 100);
        boolean passed = scorePercentage >= 50;

        String grade;
        if (scorePercentage >= 80) grade = "A";
        else if (scorePercentage >= 65) grade = "B";
        else if (scorePercentage >= 50) grade = "C";
        else grade = "F";

        Result result = resultRepository.findByStudent_IdAndAssessment_Id(studentId, assessmentId)
                .orElse(new Result());
        result.setStudent(student);
        result.setAssessment(assessment);
        result.setScore(scorePercentage);
        result.setGrade(grade);
        result.setSubmittedAt(LocalDateTime.now());
        resultRepository.save(result);

        if (passed && promote) promoteStudentToNextLevel(student);

        String message = passed ? "Hongera! Umefaulu." : "Samahani, hujafaulu. Jaribu tena.";
        return new AssessmentResultResponse(message, scorePercentage, passed);
    }

    @Override
    public List<Assessment> getAssessmentsByStudentId(Long studentId) {
        return assessmentRepository.findByStudent_Id(studentId);
    }

    @Override
    public List<Assessment> getPassedAssessmentsByStudentId(Long studentId) {
        return assessmentRepository.findByStudent_IdAndPassedTrue(studentId);
    }

    private void promoteStudentToNextLevel(User student) {
        LessonLevel currentLevel = student.getLevel();
        LessonLevel nextLevel = getNextLevel(currentLevel);
        student.setLevel(nextLevel);
        userRepository.save(student);
    }

    private LessonLevel getNextLevel(LessonLevel currentLevel) {
        if (currentLevel == null) return LessonLevel.BEGINNER;
        return switch (currentLevel) {
            case BEGINNER -> LessonLevel.INTERMEDIATE;
            case INTERMEDIATE -> LessonLevel.ADVANCED;
            case ADVANCED -> LessonLevel.ADVANCED;
        };
    }
}
