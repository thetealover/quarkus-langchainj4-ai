package com.thetealover.conversation.ws.config.properties;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "ws")
public interface ConversationWsConfigurationProperties {
  String appVersion();
}
