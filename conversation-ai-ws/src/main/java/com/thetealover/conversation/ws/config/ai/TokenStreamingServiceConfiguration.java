package com.thetealover.conversation.ws.config.ai;

import static com.thetealover.conversation.ws.config.ai.modelsupplier.claude.ClaudeStreamingLlmSupplier.getAnthropicStreamingChatModel;

import com.thetealover.conversation.ws.config.ai.qualifier.service.SportsTokenStreamingService;
import com.thetealover.conversation.ws.config.ai.qualifier.service.WeatherTokenStreamingService;
import com.thetealover.conversation.ws.config.mcp.qualifier.SportsMcpToolProvider;
import com.thetealover.conversation.ws.config.mcp.qualifier.WeatherMcpToolProvider;
import com.thetealover.conversation.ws.config.memory.RedisChatMemoryProvider;
import com.thetealover.conversation.ws.service.ai.common.service.TokenStreamingService;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class TokenStreamingServiceConfiguration {
  @Inject @WeatherMcpToolProvider ToolProvider weatherMcpToolProvider;
  @Inject @SportsMcpToolProvider ToolProvider sportsMcpToolProvider;
  @Inject RedisChatMemoryProvider redisChatMemoryProvider;

  @Produces
  @Singleton
  @WeatherTokenStreamingService
  public TokenStreamingService weatherTokenStreamingService() {

    return AiServices.builder(TokenStreamingService.class)
        .streamingChatModel(getAnthropicStreamingChatModel())
        .chatMemoryProvider(redisChatMemoryProvider)
        .toolProvider(weatherMcpToolProvider)
        .build();
  }

  @Produces
  @Singleton
  @SportsTokenStreamingService
  public TokenStreamingService sportsTokenStreamingService() {

    return AiServices.builder(TokenStreamingService.class)
        .systemMessageProvider(
            o ->
                """
                You're an expert in sports. Your mission is to help users with their sports-related questions.
                Use tools at your disposal to provide answers to user's questions.
                """)
        .streamingChatModel(getAnthropicStreamingChatModel())
        .chatMemoryProvider(redisChatMemoryProvider)
        .toolProvider(sportsMcpToolProvider)
        .build();
  }
}
