package com.thetealover.conversation.ws.config.ai;

import static com.thetealover.conversation.ws.config.ai.supplier.claude.ClaudeStreamingLlmSupplier.getAnthropicStreamingChatModel;
import static com.thetealover.conversation.ws.config.mcp.supplier.WeatherMcpToolsProviderSupplier.getWeatherMcpToolProvider;

import com.thetealover.conversation.ws.config.ai.qualifier.service.SportsTokenStreamingService;
import com.thetealover.conversation.ws.config.ai.qualifier.service.WeatherTokenStreamingService;
import com.thetealover.conversation.ws.config.memory.RedisChatMemoryProvider;
import com.thetealover.conversation.ws.service.ai.common.service.TokenStreamingService;
import dev.langchain4j.service.AiServices;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class TokenStreamingServiceConfiguration {
  @Inject RedisChatMemoryProvider redisChatMemoryProvider;

  @Produces
  @Singleton
  @WeatherTokenStreamingService
  public TokenStreamingService weatherTokenStreamingService() {

    return AiServices.builder(TokenStreamingService.class)
        .streamingChatModel(getAnthropicStreamingChatModel())
        .chatMemoryProvider(redisChatMemoryProvider)
        .toolProvider(getWeatherMcpToolProvider())
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
                ** ROLE AND MISSION **
                You're an expert in sports. Your mission is to help users with their sports-related questions.
                Use tools at your disposal to provide answers to user's questions.
                Answer to the questions in a concise and direct manner.
                Your mission is:
                    - Analyze the user's request
                    - Gather the relevant sports information
                    - Provide a concise and direct answer

                Use your tools to provide accurate, real-time sports information.

                ** RESPONSE FORMAT **
                You response MUST be structured in a format so it'll fit the markdown construction on front-end side.
                """)
        .streamingChatModel(getAnthropicStreamingChatModel())
        .chatMemoryProvider(redisChatMemoryProvider)
        .toolProvider(getWeatherMcpToolProvider())
        .build();
  }
}
