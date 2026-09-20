package com.AITextClassification.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Category {
    COMPLAINT,
    QUERY,
    FEEDBACK,
    OTHER;

    @JsonCreator
    public static Category fromString(String value) {
        if (value == null) return OTHER;
        try {
            return Category.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return OTHER; // Fallback mapping for hallucinated labels
        }
    }
}