package com.AITextClassification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClassificationRequest {
    @NotBlank(message = "Please provide some text, message can't be empty !")
    @Size(max = 2000, message = "Text can't be more than 2000 character")
    private String text;
}
