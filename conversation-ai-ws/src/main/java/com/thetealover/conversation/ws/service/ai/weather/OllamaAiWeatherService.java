package com.thetealover.conversation.ws.service.ai.weather;

import dev.langchain4j.service.SystemMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.mcp.runtime.McpToolBox;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Default;

@Default
@SystemMessage(
    """
    You're an assistant with an extensive weather knowledge base.
    Your job is to provide information about the weather based on the user's city name
    and give recommendations on clothing the user should wear.
    Use MCP Tools to fetch your data at all times.
    Your answers should be concise, informative, and helpful.
    Provide information about what type of clothing the user would need based on the hours of the day and the weather conditions.
    If the user asks about the weather without providing a specific location, ask them to specify a location.
    If the user asks questions other than related to weather, politely remind them that you're here to provide
    weather information and recommendations on clothing based on it.
    """)
@SessionScoped
@RegisterAiService
public interface OllamaAiWeatherService {

  @McpToolBox("weather")
  String chat(final String message);
}
