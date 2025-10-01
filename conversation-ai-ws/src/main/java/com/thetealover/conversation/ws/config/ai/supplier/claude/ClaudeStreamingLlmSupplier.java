package com.thetealover.conversation.ws.config.ai.supplier.claude;

import static com.thetealover.conversation.ws.config.properties.AiConfigurationPropertiesProvider.getClaudeProperties;

import com.thetealover.conversation.ws.config.properties.AiConfigurationPropertiesProvider.AiConfiguration;
import dev.langchain4j.model.anthropic.AnthropicStreamingChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import java.util.function.Supplier;

public class ClaudeStreamingLlmSupplier implements Supplier<StreamingChatModel> {

  @Override
  public StreamingChatModel get() {
    final AiConfiguration claudeProperties = getClaudeProperties();

    return AnthropicStreamingChatModel.builder()
        .modelName(claudeProperties.getModelId())
        .apiKey(claudeProperties.getApiKey())
        .temperature(claudeProperties.getTemperature())
        .topK(claudeProperties.getTopK())
        .topP(claudeProperties.getTopP())
        .maxTokens(claudeProperties.getMaxTokens())
        .logRequests(claudeProperties.getLogRequests())
        .logResponses(claudeProperties.getLogResponses())
        .timeout(claudeProperties.getTimeoutInSeconds())
        .build();
  }

  public static StreamingChatModel getAnthropicStreamingChatModel() {
    return new ClaudeStreamingLlmSupplier().get();
  }
}
