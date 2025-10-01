package com.thetealover.conversation.ws.persistence.redis;

import static com.thetealover.conversation.ws.persistence.redis.RedisStreamPublisher.END_OF_STREAM_MESSAGE;
import static com.thetealover.conversation.ws.persistence.redis.RedisStreamPublisher.ERROR_MESSAGE;
import static java.util.UUID.randomUUID;

import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.stream.ReactiveStreamCommands;
import io.quarkus.redis.datasource.stream.StreamMessage;
import io.quarkus.redis.datasource.stream.XGroupCreateArgs; // Import the correct class
import io.quarkus.redis.datasource.stream.XReadGroupArgs;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class RedisStreamConsumer {
  private final ReactiveStreamCommands<String, String, String> streamCommands;

  public RedisStreamConsumer(final ReactiveRedisDataSource reactiveRedisDataSource) {
    this.streamCommands = reactiveRedisDataSource.stream(String.class, String.class, String.class);
  }

  public Multi<String> consumeStream(final String streamKey) {
    log.info("Client connected to consume from stream: {}", streamKey);

    final String groupName = "group-%s".formatted(streamKey);
    final String consumerId = "consumer-%s".formatted(randomUUID());
    final AtomicBoolean isTerminated = new AtomicBoolean(false);

    final Uni<Void> createGroupUni =
        streamCommands
            .xgroupCreate(streamKey, groupName, "0-0", new XGroupCreateArgs().mkstream())
            .onFailure(t -> t.getMessage() != null && t.getMessage().contains("BUSYGROUP"))
            .recoverWithItem((Void) null)
            .replaceWithVoid();

    final Uni<List<StreamMessage<String, String, String>>> initialReadUni =
        streamCommands.xreadgroup(
            groupName, consumerId, Map.of(streamKey, ">"), new XReadGroupArgs().count(500));

    final Uni<List<StreamMessage<String, String, String>>> pollingReadUni =
        streamCommands.xreadgroup(
            groupName,
            consumerId,
            Map.of(streamKey, ">"),
            new XReadGroupArgs().block(Duration.ofMillis(5000)).count(1));

    final Multi<List<StreamMessage<String, String, String>>> repeatingPollingMulti =
        Multi.createBy().repeating().uni(() -> pollingReadUni).until(list -> isTerminated.get());

    Multi<List<StreamMessage<String, String, String>>> combinedStream =
        createGroupUni
            .onItem()
            .transformToMulti(
                v ->
                    Multi.createBy()
                        .concatenating()
                        .streams(initialReadUni.toMulti(), repeatingPollingMulti));

    return combinedStream
        .onItem()
        .transformToMultiAndConcatenate(
            responseList -> {
              if (responseList == null || responseList.isEmpty()) {
                return Multi.createFrom().empty();
              }
              return Multi.createFrom().iterable(responseList);
            })
        .onItem()
        .invoke(
            message ->
                streamCommands
                    .xack(streamKey, groupName, message.id())
                    .subscribe()
                    .with(
                        ack -> log.trace("ACKed message {} in group {}", message.id(), groupName),
                        err -> log.error("Failed to ACK message", err)))
        .map(message -> message.payload().get("token"))
        .onItem()
        .invoke(
            token -> {
              if (END_OF_STREAM_MESSAGE.equals(token) || ERROR_MESSAGE.equals(token)) {
                isTerminated.set(true);
              }
            })
        .onTermination()
        .invoke(
            () -> {
              log.info("Stream terminated for {}. Cleaning up consumer {}.", streamKey, consumerId);
              streamCommands
                  .xgroupDelConsumer(streamKey, groupName, consumerId)
                  .subscribe()
                  .with(
                      res -> log.info("Cleaned up consumer {}", consumerId),
                      err -> log.error("Failed to clean up consumer {}", consumerId, err));
            });
  }
}
