package com.thetealover.conversation.ws.config.ai;

import com.thetealover.conversation.ws.config.ai.qualifier.CustomOllamaStreamingChatModel;
import com.thetealover.conversation.ws.config.mcp.qualifier.WeatherMcpToolProvider;
import com.thetealover.conversation.ws.service.common.TokenStreamingAssistant;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class AiAssistantConfiguration {
  @Inject @CustomOllamaStreamingChatModel OllamaStreamingChatModel customStreamingOllamaChatModel;
  @Inject @WeatherMcpToolProvider ToolProvider mcpToolProvider;

  @Produces
  @Singleton
  public TokenStreamingAssistant tokenStreamingAssistant() {
    return AiServices.builder(TokenStreamingAssistant.class)
        .streamingChatModel(customStreamingOllamaChatModel)
        .toolProvider(mcpToolProvider)
        .build();
  }
}
