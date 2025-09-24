package com.thetealover.conversation.ws.config.properties;

import java.time.Duration;
import lombok.Builder;
import lombok.Data;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

public class AiConfigurationPropertiesProvider {

  public static AiConfiguration getClaudeProperties() {
    final Config config = ConfigProvider.getConfig();

    return AiConfiguration.builder()
        .modelId(config.getValue("ai.claude.model-id", String.class))
        .apiKey(config.getValue("ai.claude.api-key", String.class))
        .temperature(config.getValue("ai.claude.temperature", Double.class))
        .topK(config.getValue("ai.claude.top-k", Integer.class))
        .topP(config.getValue("ai.claude.top-p", Double.class))
        .maxTokens(config.getValue("ai.claude.max-output-tokens", Integer.class))
        .logRequests(config.getValue("ai.claude.log-requests", Boolean.class))
        .logResponses(config.getValue("ai.claude.log-responses", Boolean.class))
        .timeoutInSeconds(
            Duration.ofSeconds(config.getValue("ai.claude.timeout-seconds", Integer.class)))
        .build();
  }

  public static AiConfiguration getLocalOllamaProperties() {
    final Config config = ConfigProvider.getConfig();

    return AiConfiguration.builder()
        .baseUrl(config.getValue("ai.ollama-local.base-url", String.class))
        .modelId(config.getValue("ai.ollama-local.model-id", String.class))
        .temperature(config.getValue("ai.ollama-local.temperature", Double.class))
        .topK(config.getValue("ai.ollama-local.top-k", Integer.class))
        .topP(config.getValue("ai.ollama-local.top-p", Double.class))
        .maxTokens(config.getValue("ai.ollama-local.max-output-tokens", Integer.class))
        .logRequests(config.getValue("ai.ollama-local.log-requests", Boolean.class))
        .logResponses(config.getValue("ai.ollama-local.log-responses", Boolean.class))
        .build();
  }

  @Data
  @Builder
  public static class AiConfiguration {
    private String baseUrl;
    private String modelId;
    private String apiKey;
    private Double temperature;
    private Integer topK;
    private Double topP;
    private Integer maxTokens;
    private Boolean logRequests;
    private Boolean logResponses;
    private Duration timeoutInSeconds;
  }
}
