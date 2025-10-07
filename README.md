# quarkus-langchain-ai

Quarkus Application using LangChain4j AI to interact with Ollama local running models

## Pre-requisites

- JDK 21
- Docker
- AI Model
    - Anthropic Claude token
    - Ollama (for free, local use)
        - Ollama AI model `llama3-groq-tool-use`
- NPM (recommended to build and run a Chatbot UI on a local machine)

## Packaging and running the application

Running Redis and MySQL locally is required.
All the upper mentioned can be run using the Docker Compose file:

Start up the Docker Compose containers in detached mode:

```shell script
docker compose up -d
```

The application can be packaged using:

```shell script
./gradlew clean spotlessApply build
```

Running the application:

The web services should be running using `dev` profile locally.
Execute the following commands in separate terminal sessions.

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