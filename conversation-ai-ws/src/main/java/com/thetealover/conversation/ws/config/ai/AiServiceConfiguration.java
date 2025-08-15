package com.thetealover.conversation.ws.config.ai;

import com.thetealover.conversation.ws.config.ai.qualifier.WeatherBlockingAiService;
import com.thetealover.conversation.ws.config.ai.qualifier.WeatherOllamaChatModel;
import com.thetealover.conversation.ws.config.mcp.qualifier.WeatherMcpToolProvider;
import com.thetealover.conversation.ws.service.common.BlockingAiService;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class AiServiceConfiguration {
  @Inject @WeatherOllamaChatModel ChatModel customOllamaChatModel;
  @Inject @WeatherMcpToolProvider ToolProvider mcpToolProvider;

  @Produces
  @WeatherBlockingAiService
  public BlockingAiService weatherBlockingAiService() {
    return AiServices.builder(BlockingAiService.class)
        .chatModel(customOllamaChatModel)
        .toolProvider(mcpToolProvider)
        .systemMessageProvider(
            chatMemoryId ->
                """
                  You're an assistant with an extensive weather knowledge base.
                  Your job is to provide information about the weather based on the user's city
                  and provide recommendations on clothing the user should wear.
                  Your answers should be based on the information collected using the MCP Tools. Provide concise answers.
                  If the user asks about the weather without providing a specific city name, ask them to provide you with it.
                  If the user asks irrelevant questions, politely remind them that you're here to provide weather information and recommendations on clothing.
                  """)
        .build();
  }
}
