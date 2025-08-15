package com.thetealover.conversation.ws.config.properties;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "mcp")
public interface McpConfigurationProperties {
  String baseUrl();

  Long timeoutInSeconds();

  Boolean logRequests();

  Boolean logResponses();
}
