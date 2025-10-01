package com.thetealover.conversation.ws.config.memory;

import static com.thetealover.conversation.ws.config.properties.ConversationWsConfigurationPropertiesProvider.getConversationWsConfigurationProperties;

import com.thetealover.conversation.ws.config.properties.ConversationWsConfigurationPropertiesProvider.ConversationWsConfigurationProperties;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class RedisChatMemoryProvider implements ChatMemoryProvider {
  @Inject RedisChatMemoryStore redisChatMemoryStore;

  @Override
  public ChatMemory get(final Object memoryId) {
    final ConversationWsConfigurationProperties wsConfigurationProperties =
        getConversationWsConfigurationProperties();

    return MessageWindowChatMemory.builder()
        .id(memoryId)
        .chatMemoryStore(redisChatMemoryStore)
        .maxMessages(wsConfigurationProperties.getChatMemoryMaxMessages())
        .build();
  }
}
