package com.thetealover.conversation.ws.config.properties;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "ai")
public interface AiConfigurationProperties {
  String ollamaBaseUrl();

  AiServiceProperties weatherAdvisor();

  interface AiServiceProperties {
    String modelId();

    Integer maxOutputTokens();

    Double temperature();

    Double topP();

    Integer topK();
  }
}
