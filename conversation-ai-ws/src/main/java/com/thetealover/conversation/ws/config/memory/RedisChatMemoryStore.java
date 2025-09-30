package com.thetealover.conversation.ws.config.memory;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.thetealover.conversation.ws.config.serde.CustomChatMessageCodec;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Singleton
public class RedisChatMemoryStore implements ChatMemoryStore {
  private final CustomChatMessageCodec chatMessageCodec;
  private final JedisPool jedisPool;

  public RedisChatMemoryStore(
      @ConfigProperty(name = "quarkus.redis.hosts") final URI redisUri,
      final CustomChatMessageCodec chatMessageCodec) {
    // Manually create a Jedis connection pool - not managed by  Quarkus's reactive engine
    this.jedisPool = new JedisPool(new JedisPoolConfig(), redisUri);
    this.chatMessageCodec = chatMessageCodec;
  }

  @Override
  public void deleteMessages(final Object memoryId) {
    try (Jedis jedis = jedisPool.getResource()) {
      jedis.del(memoryId.toString());
    }
  }

  @Override
  public List<ChatMessage> getMessages(final Object memoryId) {
    String commands;
    try (Jedis jedis = jedisPool.getResource()) {
      // Jedis won't throw an exception on a blocking network call.
      commands = jedis.get(memoryId.toString());
    }

    if (isNull(commands) || commands.isEmpty()) {
      return Collections.emptyList();
    }
    return chatMessageCodec.messagesFromJson(commands);
  }

  @Override
  public void updateMessages(final Object memoryId, final List<ChatMessage> messages) {
    final String jsonPayload = chatMessageCodec.messagesToJson(messages);
    try (Jedis jedis = jedisPool.getResource()) {
      jedis.set(memoryId.toString(), jsonPayload);
    }
  }

  @PreDestroy
  public void cleanUp() {
    // Ensuring the pool is closed on shutdown
    if (nonNull(jedisPool)) {
      jedisPool.close();
    }
  }
}
