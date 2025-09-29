# quarkus-langchain-ai

Quarkus Application using LangChain4j AI to interact with Ollama local running models

## Pre-requisites

- JDK 21
- Docker
- Ollama
    - Ollama AI model `llama3-groq-tool-use`
- NPM (Optional for chatbot-ui compilation)

## Packaging and running the application

Running Redis locally is required.
Redis can be started up using the Docker Compose file:

```shell script
docker compose up -d
```

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

## Ease of use

You can use the `chatbot-ui` ReactJS application to interact with the web services.

To install the project:

```shell script
npm install
```

To run the application locally:
```shell script
npm run dev
```