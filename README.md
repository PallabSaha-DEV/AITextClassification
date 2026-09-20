# AITextClassification
A lightweight Spring Boot service that classifies input text using local Large Language Models (LLMs) via Spring AI and Ollama to intelligently classify unstructured text.  

The API evaluates incoming text and categorizes it into one of four distinct buckets: `COMPLAINT`, `QUERY`, `FEEDBACK`, or `OTHER`, returning the result alongside an AI-generated confidence score.

## Tech Stack
* **Framework:** Java 21, Spring Boot 3
* **AI Integration:** Spring AI (`spring-ai-ollama-spring-boot-starter`)
* **LLM Engine:** Ollama (Local execution, zero API costs)
* **Validation:** Spring Boot Starter Validation
* **Tooling:** Lombok, Maven

## 📋 Prerequisites
1. **Java** installed.
2. **Maven** installed.
3. **Ollama** installed locally (Download from [ollama.com](https://ollama.com)).

## Getting Started

### 1. Start the Local AI Model
Before starting the Spring Boot application, ensure Ollama is running and has the required model downloaded. Open your terminal and run:
```bash
ollama run phi3
# Note: You can change this to llama3.2 or any other model in application.properties
