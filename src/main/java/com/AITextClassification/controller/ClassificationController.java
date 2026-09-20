package com.AITextClassification.controller;

import com.AITextClassification.dto.ClassificationRequest;
import com.AITextClassification.dto.ClassificationResponse;
import com.AITextClassification.service.ClassificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.AITextClassification.Constants.Endpoints.ENDPOINT;

@RestController
@RequestMapping(ENDPOINT)
public class ClassificationController {
    private final ClassificationService classificationService;

    public ClassificationController(ClassificationService classificationService) {
        this.classificationService = classificationService;
    }

    @PostMapping("/validate")
    public ResponseEntity<ClassificationResponse> classifyText(@Valid @RequestBody ClassificationRequest request) {
        ClassificationResponse response = classificationService.classify(request.getText());
        return ResponseEntity.ok(response);
    }
}
