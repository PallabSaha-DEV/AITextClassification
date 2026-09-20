package com.AITextClassification.service;

import com.AITextClassification.dto.ClassificationResponse;
import com.AITextClassification.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ClassificationService {
    private static final Logger log = LoggerFactory.getLogger(ClassificationService.class);
    private final ChatModel chatModel;
    private final BeanOutputConverter<ClassificationResponse> outputConverter;

    public ClassificationService(ChatModel chatModel) {
        this.chatModel = chatModel;
        this.outputConverter = new BeanOutputConverter<>(ClassificationResponse.class);
    }

    public ClassificationResponse classify(String inputText) {
        String templateString = """
            You are a strict text classification system.
            Analyze the following text and assign it to exactly ONE category from this list:
            - COMPLAINT: Expressing dissatisfaction, operational issues, or broken features.
            - QUERY: Asking a question, seeking help, or requesting information.
            - FEEDBACK: General remarks, compliments, or suggestions for improvement.
            - OTHER: Anything ambiguous, greeting-like, or outside the above scopes.
            
            Provide a confidence score formatted Exactly as (e.g., "95.5", "80", "100") between 0 to 100 ..
            
            Text to classify:
            "{text}"
            
            {format}
            """;

        PromptTemplate promptTemplate = new PromptTemplate(templateString);
        Prompt prompt = promptTemplate.create(Map.of(
                "text", inputText,
                "format", outputConverter.getFormat()
        ));

        try {
            String rawResponse = chatModel.call(prompt).getResult().getOutput().getText();
            log.info("Raw LLM output: {}", rawResponse);

            ClassificationResponse response = outputConverter.convert(rawResponse);

            log.info("Raw response converted successfully : {}", response);

            // Defend against missing/null fields
            if (response == null || response.getCategory() == null) {
                return ClassificationResponse.builder()
                        .category(Category.OTHER)
                        .confidence("50%")
                        .build();
            }
            log.info("Null check Done");

            // Normalize confidence to 2 decimal places if needed
            double normalizedConfidence = Math.min(100.0, Math.max(0.0, Double.parseDouble(response.getConfidence())));
            log.info("normalizedConfidence : {}", normalizedConfidence);
            response.setConfidence(String.valueOf(Math.round(normalizedConfidence * 100.0) / 100.0)+"%");
            log.info("Response converted successfully : {}", response);

            return response;

        } catch (Exception ex) {
            log.error("Failed to parse classification from AI. Falling back to default.", ex);
            return ClassificationResponse.builder()
                    .category(Category.OTHER)
                    .confidence("0%")
                    .build();
        }
    }
}
