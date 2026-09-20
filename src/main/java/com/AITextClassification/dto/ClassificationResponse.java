package com.AITextClassification.dto;

import com.AITextClassification.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ClassificationResponse {
    private Category category;
    private String confidence;
}
