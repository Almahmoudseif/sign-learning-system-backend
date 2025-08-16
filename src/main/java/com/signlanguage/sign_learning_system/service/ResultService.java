package com.signlanguage.sign_learning_system.service;

import com.signlanguage.sign_learning_system.DTO.PassedLessonDTO;
import com.signlanguage.sign_learning_system.model.Assessment;
import com.signlanguage.sign_learning_system.model.Result;
import com.signlanguage.sign_learning_system.model.User;
import com.signlanguage.sign_learning_system.repository.AssessmentRepository;
import com.signlanguage.sign_learning_system.repository.ResultRepository;
import com.signlanguage.sign_learning_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResultService {

    private final ResultRepository resultRepository;
    private final UserRepository userRepository;
    private final AssessmentRepository assessmentRepository;

    public ResultService(ResultRepository resultRepository,
                         UserRepository userRepository,
                         AssessmentRepository assessmentRepository) {
        this.resultRepository = resultRepository;
        this.userRepository = userRepository;
        this.assessmentRepository = assessmentRepository;
    }

    public Result saveResult(Result result) {
        return resultRepository.save(result);
    }

    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    public Optional<Result> getResultById(Long id) {
        return resultRepository.findById(id);
    }

    public List<Result> getResultsByStudentId(Long studentId) {
        return resultRepository.findByStudent_Id(studentId);
    }

    public List<Result> getResultsByAssessmentId(Long assessmentId) {
        return resultRepository.findByAssessment_Id(assessmentId);
    }

    public Optional<Result> getResultByStudentAndAssessment(Long studentId, Long assessmentId) {
        return resultRepository.findByStudent_IdAndAssessment_Id(studentId, assessmentId);
    }

    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }

    /**
     * Hii ndiyo LOGIC MPYA: huchunguza kama result ipo (student+assessment).
     * Ikiwa ipo -> inai-update (score/grade/submittedAt).
     * Ikiwa haipo -> ina-insert mpya.
     */
    @Transactional
    public Result saveOrUpdateResult(Long studentId, Long assessmentId, double score, String grade) {
        Result result = resultRepository.findByStudent_IdAndAssessment_Id(studentId, assessmentId)
                .orElseGet(() -> {
                    Result r = new Result();
                    User student = userRepository.findById(studentId)
                            .orElseThrow(() -> new RuntimeException("Student not found: " + studentId));
                    Assessment assessment = assessmentRepository.findById(assessmentId)
                            .orElseThrow(() -> new RuntimeException("Assessment not found: " + assessmentId));
                    r.setStudent(student);
                    r.setAssessment(assessment);
                    return r;
                });

        result.setScore(score);
        result.setGrade(grade);
        result.setSubmittedAt(LocalDateTime.now());

        return resultRepository.save(result);
    }

    // Passed lessons (historia)
    public List<PassedLessonDTO> getPassedLessonsByStudent(Long studentId) {
        List<Result> results = resultRepository.findByStudent_Id(studentId);

        return results.stream()
                .filter(result -> result.getScore() >= 50) // kizingiti cha kufaulu (badilisha ukitaka)
                .map(result -> {
                    var lesson = result.getAssessment().getLesson();
                    return new PassedLessonDTO(
                            lesson.getId(),
                            lesson.getTitle(),
                            lesson.getLevel() != null ? lesson.getLevel().name() : null,
                            lesson.getLessonType(),
                            lesson.getContentUrl(),
                            result.getScore(),
                            result.getGrade(),
                            result.getSubmittedAt()
                    );
                })
                .collect(Collectors.toList());
    }

    // 🔹 Mpya: kupata matokeo yote ya wanafunzi kwa mwalimu
    public List<Result> getResultsByTeacher(Long teacherId) {
        return resultRepository.findByAssessment_Teacher_Id(teacherId);
    }
}
