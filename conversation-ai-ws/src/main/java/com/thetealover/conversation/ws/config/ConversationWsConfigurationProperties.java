package com.thetealover.conversation.ws.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "ws")
public interface ConversationWsConfigurationProperties {
  String appVersion();
}
