package com.signlanguage.sign_learning_system.DTO;

import com.signlanguage.sign_learning_system.enums.LessonLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAssessmentRequest {
    private String title;
    private String description;
    private LessonLevel level;
    private Long lessonId;
    private Long teacherId;
}
