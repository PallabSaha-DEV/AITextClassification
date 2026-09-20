# AITextClassification
A lightweight Spring Boot service that classifies input text using local Large Language Models (LLMs) via Spring AI and Ollama to intelligently classify unstructured text.  

The API evaluates incoming text and categorizes it into one of four distinct buckets: `COMPLAINT`, `QUERY`, `FEEDBACK`, or `OTHER`, returning the result alongside an AI-generated confidence score.

## Tech Stack
* **Framework:** Java 21, Spring Boot 3
* **AI Integration:** Spring AI (`spring-ai-ollama-spring-boot-starter`)
* **LLM Engine:** Ollama (Local execution, zero API costs)
* **Validation:** Spring Boot Starter Validation
* **Tooling:** Lombok, Maven

## Prerequisites
1. **Java** installed.
2. **Maven** installed.
3. **Ollama** installed locally (Download from [ollama.com](https://ollama.com)).

## Getting Started
```
1. Start the Local AI Model
Before starting the Spring Boot application, ensure Ollama is running and has the required model downloaded. Open your terminal and run:
bash
ollama run phi3
# Note: You can change this to llama3.2 or any other model in application.properties

2. Configure Application Properties if required
server.port=8080
spring.application.name=ai-classifier

Ollama Endpoint & Model
spring.ai.ollama.base-url=
spring.ai.ollama.chat.options.model=

Determinism & Output Controls (Prevents Hallucinations)
spring.ai.ollama.chat.options.temperature=0.0
spring.ai.ollama.chat.options.format=json

API Examples:
**Method:** POST
**Path:** /api/v1/classify/validate
**Header:** Content-Type: application/json
Example :
** Input: **
{
    "text": "My payment went through twice and customer support is not responding!"
}
**Response:**
{
    "category": "COMPLAINT",
    "confidence": "99.5%"
}
How AI Was Used

1. Framework Abstraction with Spring AI
The project uses the `spring-ai-ollama-spring-boot-starter` library. Instead of manually writing HTTP client logic to interact with LLM endpoints, Spring AI provides a unified `ChatModel` interface. This abstracts the model provider—allowing seamless switching between local models (`phi3`, `llama3.2`) or cloud providers without altering core Java business logic.

2. Hardened System Prompting
To eliminate conversational replies, the text input is wrapped in a strict system prompt containing defensive rules:
* **Persona Lockdown:** Instructs the LLM that it is an automated classification engine and explicitly forbids it from answering questions or engaging in conversational banter.
* **Fallback Boundaries:** Explicitly directs the model to categorize non-business, conversational, or nonsensical input (e.g., *"Do I like you?"*) strictly as `OTHER`.
* **Format Enforcement:** Specifies the allowed category enum values and dictates that the confidence score must be returned as a percentage string (e.g., `95.0%`).

3. Structured Output & Type Safety
The service uses Spring AI’s `BeanOutputConverter<ClassificationResponse>`, which:
* Dynamically generates JSON schema requirements appended to the prompt.
* Deserializes the raw JSON response directly into strongly typed Java DTOs and Enums (`Category.java`).

4. Hallucination & Drift Mitigation
Two engine-level parameters are configured in `application.properties`:
* **`temperature: 0.0`**: Removes stochastic sampling, forcing the model to select the highest-probability tokens. This makes classification output deterministic and reproducible.
* **`format: json`**: Enforces strict grammar-level JSON output at the Ollama engine layer, preventing conversational prefixes (such as *"Sure, here is your classification:"*) from breaking Jackson JSON parsing.
```
