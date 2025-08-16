package com.signlanguage.sign_learning_system.DTO;

import java.util.Map;

public class AssessmentSubmissionRequest {

    private Long studentId;
    private Long assessmentId;
    private Map<Long, Long> answers; // ✅ Key: questionId, Value: selected answerId

    public AssessmentSubmissionRequest() {
    }

    public AssessmentSubmissionRequest(Long studentId, Long assessmentId, Map<Long, Long> answers) {
        this.studentId = studentId;
        this.assessmentId = assessmentId;
        this.answers = answers;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public Map<Long, Long> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, Long> answers) {
        this.answers = answers;
    }
}
