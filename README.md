# quarkus-langchain-ai

Quarkus Application using LangChain4j AI to interact with Ollama local running models

## Pre-requisites

- JDK 21
- Ollama
    - Ollama AI model `llama3-groq-tool-use`

## Packaging and running the application

The application can be packaged using:

```shell script
./gradlew clean spotlessApply build
```

Running the application:

The web services should be running using `dev` profile locally.
Execute the following commands in seperate terminal sessions.

```shell script
./gradlew clean spotlessApply mcp-ws:quarkusDev 
```

```shell script
./gradlew clean spotlessApply conversation-ai-ws:quarkusDev 
```

