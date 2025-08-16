package com.signlanguage.sign_learning_system.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String level;
    private String teacherName;
    private int totalQuestions;
    private String lessonTitle; // hii sasa ipo
}
