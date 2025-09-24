package com.thetealover.conversation.ws.config.properties;

import java.time.Duration;
import lombok.Builder;
import lombok.Data;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

public class McpConfigurationPropertiesProvider {

  public static McpConfigurationProperties getMcpConfigurationProperties() {
    final Config config = ConfigProvider.getConfig();

    return McpConfigurationProperties.builder()
        .baseUrl(config.getValue("mcp.base-url", String.class))
        .timeoutInSeconds(Duration.ofSeconds(config.getValue("mcp.timeout-in-seconds", Long.class)))
        .logRequests(config.getValue("mcp.log-requests", Boolean.class))
        .logResponses(config.getValue("mcp.log-responses", Boolean.class))
        .build();
  }

  @Data
  @Builder
  public static class McpConfigurationProperties {
    private String baseUrl;
    private Duration timeoutInSeconds;
    private Boolean logRequests;
    private Boolean logResponses;
  }
}
