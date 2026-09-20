package com.AITextClassification;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AiTextClassificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiTextClassificationApplication.class, args);
	}

}
