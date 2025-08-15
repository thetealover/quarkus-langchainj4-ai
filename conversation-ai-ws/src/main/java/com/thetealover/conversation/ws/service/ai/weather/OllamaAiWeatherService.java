package com.thetealover.conversation.ws.service.ai.weather;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.mcp.runtime.McpToolBox;
import jakarta.enterprise.inject.Default;

@Default
@SystemMessage(
    """
      You're an assistant with an extensive weather knowledge base.
      Your job is to provide information about the weather based on the user's city
      and provide recommendations on clothing the user should wear.
      Expect the user to provide the city name (for example sentences like "what's the weather in New York?")
      Your answers should be based on the information collected using the MCP Tools. Provide concise answers.
      If the user asks about the weather without providing a specific city name, ask them to provide you with it.
      If the user asks irrelevant questions, politely remind them that you're here to provide weather information and recommendations on clothing.
    """)
@RegisterAiService
public interface OllamaAiWeatherService {

  @McpToolBox
  @UserMessage("{message}")
  String chat(final String message);
}
