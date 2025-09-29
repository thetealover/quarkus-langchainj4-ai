package com.thetealover.conversation.ws.adapter.in.api;

import static java.time.temporal.ChronoUnit.MILLIS;
import static java.util.UUID.randomUUID;

import com.thetealover.conversation.ws.adapter.in.api.model.StreamCreationResponseDto;
import com.thetealover.conversation.ws.adapter.in.api.model.ai.AiRequestDto;
import com.thetealover.conversation.ws.adapter.in.api.model.ai.AiResponseDto;
import com.thetealover.conversation.ws.config.ai.qualifier.service.SportsTokenStreamingService;
import com.thetealover.conversation.ws.config.ai.qualifier.service.WeatherTokenStreamingService;
import com.thetealover.conversation.ws.service.ai.BlockingAiF1Service;
import com.thetealover.conversation.ws.service.ai.BlockingAiGeneralService;
import com.thetealover.conversation.ws.service.ai.common.service.TokenStreamingService;
import com.thetealover.conversation.ws.service.ai.imperative.StreamingAiWeatherService;
import com.thetealover.conversation.ws.service.redis.RedisStreamConsumer;
import com.thetealover.conversation.ws.service.redis.RedisStreamPublisher;
import dev.langchain4j.service.TokenStream;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.RestStreamElementType;

@Slf4j
@Path("/ai")
public class AiController {
  @Inject StreamingAiWeatherService ollamaAiWeatherService;
  @Inject BlockingAiGeneralService blockingAiGeneralService;
  @Inject BlockingAiF1Service blockingAiF1Service;
  @Inject @WeatherTokenStreamingService TokenStreamingService weatherTokenStreamingService;
  @Inject @SportsTokenStreamingService TokenStreamingService sportsTokenStreamingService;
  @Inject RedisStreamPublisher redisStreamPublisher;
  @Inject RedisStreamConsumer redisStreamConsumer;

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

  @POST
  @Path("imperative/stream/weather")
  public StreamCreationResponseDto chatWithWeatherService(
      @Valid @NotNull final AiRequestDto request) {
    log.info("Received request to start weather stream: {}", request);

    final String streamKey = "weather-stream:%s".formatted(randomUUID());
    final TokenStream tokenStream =
        weatherTokenStreamingService.chat(request.getUserId(), request.getMessage());

    redisStreamPublisher.publish(streamKey, tokenStream);

    log.info("Started streaming to Redis stream key: {}", streamKey);
    return new StreamCreationResponseDto(streamKey);
  }

  @GET
  @Path("/stream/{streamKey}")
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @RestStreamElementType(MediaType.TEXT_PLAIN)
  public Multi<String> consumeStream(@PathParam("streamKey") final String streamKey) {
    return redisStreamConsumer.consumeStream(streamKey);
  }

  @POST
  @Path("imperative/stream/sports")
  public StreamCreationResponseDto chatWithSportsService(
      @Valid @NotNull final AiRequestDto request) {
    log.info("Received request to start sports stream: {}", request);

    final String streamKey = "sports-stream:%s".formatted(randomUUID());
    final TokenStream tokenStream =
        sportsTokenStreamingService.chat(request.getUserId(), request.getMessage());

    redisStreamPublisher.publish(streamKey, tokenStream);

    log.info("Started streaming to Redis stream key: {}", streamKey);
    return new StreamCreationResponseDto(streamKey);
  }
}
