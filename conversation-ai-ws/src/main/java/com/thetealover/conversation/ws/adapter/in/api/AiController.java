package com.thetealover.conversation.ws.adapter.in.api;

import static java.time.temporal.ChronoUnit.MILLIS;

import com.thetealover.conversation.ws.adapter.in.api.model.AiRequestDto;
import com.thetealover.conversation.ws.adapter.in.api.model.AiResponseDto;
import com.thetealover.conversation.ws.service.ai.BlockingAiF1Service;
import com.thetealover.conversation.ws.service.ai.BlockingAiGeneralService;
import com.thetealover.conversation.ws.service.ai.imperative.StreamingAiWeatherService;
import io.smallrye.mutiny.Multi;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/ai")
@RequiredArgsConstructor
public class AiController {
  private final StreamingAiWeatherService ollamaAiWeatherService;
  private final BlockingAiGeneralService blockingAiGeneralService;
  private final BlockingAiF1Service blockingAiF1Service;

  @POST
  @Path("blocking/general")
  public AiResponseDto askQuestion(@Valid @NotNull final AiRequestDto request) {
    log.info("Received request: {}", request);

    final Instant start = Instant.now();
    final String llmResponse = blockingAiGeneralService.askQuestion(request.getMessage());
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
  @Path("blocking/f1")
  public AiResponseDto askQuestionAboutF1(@Valid @NotNull final AiRequestDto request) {
    log.info("Received request: {}", request);

    final Instant start = Instant.now();
    final String llmResponse = blockingAiF1Service.searchF1Drivers(request.getMessage());
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
  @Path("stream/weather")
  public Multi<String> askForWeather(@Valid @NotNull final AiRequestDto request) {
    log.info("Received request: {}", request);

    return ollamaAiWeatherService.chat(request.getMessage());
  }
}
