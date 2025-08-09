package com.signlanguage.sign_learning_system.DTO;

public class AssessmentResultResponse {
    private String message;
    private double score;
    private boolean passed;

    public AssessmentResultResponse(String message, double score, boolean passed) {
        this.message = message;
        this.score = score;
        this.passed = passed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
