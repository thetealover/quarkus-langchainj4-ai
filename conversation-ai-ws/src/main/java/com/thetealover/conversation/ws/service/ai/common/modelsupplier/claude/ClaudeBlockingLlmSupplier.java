package com.thetealover.conversation.ws.service.ai.common.modelsupplier.claude;

import static com.thetealover.conversation.ws.config.properties.AiConfigurationPropertiesProvider.getClaudeProperties;

import com.thetealover.conversation.ws.config.properties.AiConfigurationPropertiesProvider.AiConfiguration;
import dev.langchain4j.model.anthropic.AnthropicChatModel;
import dev.langchain4j.model.chat.ChatModel;
import java.util.function.Supplier;

public class ClaudeBlockingLlmSupplier implements Supplier<ChatModel> {

  @Override
  public ChatModel get() {
    final AiConfiguration claudeProperties = getClaudeProperties();

    return AnthropicChatModel.builder()
        .modelName(claudeProperties.getModelId())
        .apiKey(claudeProperties.getApiKey())
        .temperature(claudeProperties.getTemperature())
        .topK(claudeProperties.getTopK())
        .topP(claudeProperties.getTopP())
        .maxTokens(claudeProperties.getMaxTokens())
        .logRequests(claudeProperties.getLogRequests())
        .logResponses(claudeProperties.getLogResponses())
        .build();
  }
}
