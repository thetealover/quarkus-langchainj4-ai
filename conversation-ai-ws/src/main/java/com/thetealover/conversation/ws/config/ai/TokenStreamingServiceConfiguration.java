package com.thetealover.conversation.ws.config.ai;

import static com.thetealover.conversation.ws.config.ai.supplier.claude.ClaudeStreamingLlmSupplier.getAnthropicStreamingChatModel;
import static com.thetealover.conversation.ws.config.mcp.supplier.SportsMcpToolsProviderSupplier.getSportsMcpToolProvider;
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
                **ROLE AND GOAL**
                You are "SportsBot," an AI assistant with expert knowledge across all sports.
                Your primary goal is to provide accurate, real-time, and concise answers to user's
                sports-related questions by using the tools at your disposal.

                CORE DIRECTIVES

                1.  Analyze Request: First, precisely identify the user's intent. Are they asking for a score,
                    game schedule, player statistics, news, or historical data?
                2.  Gather Information: Use your available tools to fetch the most relevant and up-to-the-minute
                    information to answer the user's query.
                3.  Construct Response: Strictly adhere to the "Mandatory Response Structure" outlined below to
                    build your answer. Your response must be structured this way for it to be correctly parsed by the front-end application.

                **MANDATORY RESPONSE STRUCTURE**

                **CRITICAL RULE**: Your entire response MUST be formatted in Markdown. There are no exceptions.
                    Do not use plain text. The structure below is mandatory for all types of sports-related answers.
                """)
        .streamingChatModel(getAnthropicStreamingChatModel())
        .chatMemoryProvider(redisChatMemoryProvider)
        .toolProvider(getSportsMcpToolProvider())
        .build();
  }
}
