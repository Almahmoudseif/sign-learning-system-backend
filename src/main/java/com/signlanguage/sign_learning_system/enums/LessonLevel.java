package com.signlanguage.sign_learning_system.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LessonLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED;

    @JsonCreator
    public static LessonLevel fromString(String value) {
        return LessonLevel.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String getValue() {
        return this.name();
    }
}
