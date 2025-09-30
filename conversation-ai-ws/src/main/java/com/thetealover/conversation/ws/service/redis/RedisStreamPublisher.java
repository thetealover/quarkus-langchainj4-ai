package com.thetealover.conversation.ws.service.redis;

import dev.langchain4j.service.TokenStream;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.stream.ReactiveStreamCommands;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class RedisStreamPublisher {
  private final ReactiveStreamCommands<String, String, String> streamCommands;

  public RedisStreamPublisher(final ReactiveRedisDataSource reactiveRedisDataSource) {
    this.streamCommands = reactiveRedisDataSource.stream(String.class, String.class, String.class);
  }

  private static final String TOKEN_FIELD = "token";
  public static final String END_OF_STREAM_MESSAGE = "[END_OF_STREAM]";
  public static final String ERROR_MESSAGE = "[ERROR]";

  public void publish(String streamKey, TokenStream tokenStream) {
    tokenStream
        .onPartialResponse(
            token -> {
              token = token.replace(" ", "%20");
              streamCommands
                  .xadd(streamKey, Map.of(TOKEN_FIELD, token))
                  .subscribe()
                  .with(
                      messageId -> log.trace("Published token to {}: {}", streamKey, messageId),
                      failure -> log.error("Failed to publish token to {}", streamKey, failure));
            })
        .onCompleteResponse(
            response ->
                streamCommands
                    .xadd(streamKey, Map.of(TOKEN_FIELD, END_OF_STREAM_MESSAGE))
                    .subscribe()
                    .with(
                        messageId -> log.info("Published END_OF_STREAM to {}", streamKey),
                        failure ->
                            log.error("Failed to publish END_OF_STREAM to {}", streamKey, failure)))
        .onError(
            error -> {
              log.error("Error during token streaming for {}", streamKey, error);
              streamCommands
                  .xadd(streamKey, Map.of(TOKEN_FIELD, ERROR_MESSAGE))
                  .subscribe()
                  .with(
                      messageId -> log.warn("Published ERROR to {}", streamKey),
                      failure -> log.error("Failed to publish ERROR to {}", streamKey, failure));
            })
        .beforeToolExecution(
            beforeToolExecution ->
                log.info("Got ToolExecutionRequest: {}", beforeToolExecution.request()))
        .onToolExecuted(
            toolExecution -> log.info("Tool execution result: {}", toolExecution.result()))
        .start();
  }
}
