package com.thetealover.conversation.ws.config.memory;

import static java.util.Objects.isNull;

import com.fasterxml.jackson.core.type.TypeReference;
import com.thetealover.conversation.ws.config.serde.CustomChatMessageCodec;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.KeyCommands;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class RedisChatMemoryStore implements ChatMemoryStore {
  private final CustomChatMessageCodec chatMessageCodec;

  private final ValueCommands<String, String> valueCommands;
  private final KeyCommands<String> keyCommands;

  public RedisChatMemoryStore(
      final CustomChatMessageCodec chatMessageCodec, final RedisDataSource redisDataSource) {
    this.chatMessageCodec = chatMessageCodec;
    this.valueCommands = redisDataSource.value(new TypeReference<>() {});
    this.keyCommands = redisDataSource.key(String.class);
  }

  @Override
  public void deleteMessages(final Object memoryId) {
    // Remove all messages associated with the memoryId
    // This is called when the exchange ends or the memory is no longer needed, like when the scope
    // is terminated.
    keyCommands.del(memoryId.toString());
  }

  @Override
  public List<ChatMessage> getMessages(final Object memoryId) {
    // Retrieve messages associated with the memoryId
    final String commands = valueCommands.get(memoryId.toString());
    if (isNull(commands) || commands.isEmpty()) {
      return Collections.emptyList();
    }
    return chatMessageCodec.messagesFromJson(commands);
  }

  @Override
  public void updateMessages(final Object memoryId, final List<ChatMessage> messages) {
    // Store messages associated with the memoryId
    valueCommands.set(memoryId.toString(), chatMessageCodec.messagesToJson(messages));
  }
}
