package com.thetealover.conversation.ws.config.properties;

import lombok.Builder;
import lombok.Data;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

public class ConversationWsConfigurationPropertiesProvider {

  public static ConversationWsConfigurationProperties getConversationWsConfigurationProperties() {
    final Config config = ConfigProvider.getConfig();

    return ConversationWsConfigurationProperties.builder()
        .appVersion(config.getValue("ws.app-version", String.class))
        .chatMemoryMaxMessages(config.getValue("ai.chat-memory.max-messages", Integer.class))
        .build();
  }

  @Data
  @Builder
  public static class ConversationWsConfigurationProperties {
    private String appVersion;
    private Integer chatMemoryMaxMessages;
  }
}
