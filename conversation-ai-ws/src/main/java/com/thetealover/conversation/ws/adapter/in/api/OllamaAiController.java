package com.thetealover.conversation.ws.adapter.in.api;

import static java.time.temporal.ChronoUnit.MILLIS;

import com.thetealover.conversation.ws.adapter.in.api.model.AiRequestDto;
import com.thetealover.conversation.ws.adapter.in.api.model.AiResponseDto;
import com.thetealover.conversation.ws.service.ai.OllamaLlmDatabaseService;
import com.thetealover.conversation.ws.service.ai.OllamaLlmF1Service;
import com.thetealover.conversation.ws.service.ai.OllamaLlmGeneralService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/ollama")
@RequiredArgsConstructor
public class OllamaAiController {
  private final OllamaLlmDatabaseService databaseAiService;
  private final OllamaLlmGeneralService generalAiService;
  private final OllamaLlmF1Service f1AiService;

  @POST
  @Path("database")
  public AiResponseDto request(@Valid @NotNull final AiRequestDto request) {
    log.info("Received request: {}", request);

    final Instant start = Instant.now();
    final String llmResponse = databaseAiService.askAdviseOnDatabaseSelection(request.getMessage());
    final Instant end = Instant.now();

    final AiResponseDto response =
        AiResponseDto.builder()
            .response(llmResponse)
            .thinkingTimeInMillis(MILLIS.between(start, end))
            .build();

    log.info("Responding with: {}", response);
    return response;
  }

  @POST
  @Path("general")
  public AiResponseDto askQuestion(@Valid @NotNull final AiRequestDto request) {
    log.info("Received request: {}", request);

    final Instant start = Instant.now();
    final String llmResponse = generalAiService.askQuestion(request.getMessage());
    final Instant end = Instant.now();

    final AiResponseDto response =
        AiResponseDto.builder()
            .response(llmResponse)
            .thinkingTimeInMillis(MILLIS.between(start, end))
            .build();

    log.info("Responding with: {}", response);
    return response;
  }

  @POST
  @Path("f1")
  public AiResponseDto askQuestionAboutF1(@Valid @NotNull final AiRequestDto request) {
    log.info("Received request: {}", request);

    final Instant start = Instant.now();
    final String llmResponse = f1AiService.searchF1Drivers(request.getMessage());
    final Instant end = Instant.now();

    final AiResponseDto response =
        AiResponseDto.builder()
            .response(llmResponse)
            .thinkingTimeInMillis(MILLIS.between(start, end))
            .build();

    log.info("Responding with: {}", response);
    return response;
  }
}
