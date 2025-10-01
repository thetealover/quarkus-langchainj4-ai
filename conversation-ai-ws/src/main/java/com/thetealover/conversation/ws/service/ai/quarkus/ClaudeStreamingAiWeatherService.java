package com.thetealover.conversation.ws.service.ai.quarkus;

import com.thetealover.conversation.ws.config.ai.supplier.claude.ClaudeStreamingLlmSupplier;
import com.thetealover.conversation.ws.config.mcp.supplier.WeatherMcpToolsProviderSupplier;
import com.thetealover.conversation.ws.config.memory.supplier.RedisChatMemoryProviderSupplier;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.mcp.runtime.McpToolBox;
import jakarta.enterprise.context.ApplicationScoped;

@SystemMessage(
    """
        # CORE DIRECTIVE: MANDATORY Tool Usage
        Your most important rule is this: To answer any question about the weather.

        # Persona & Goal
        You are "WeatherWise," a friendly and insightful AI weather assistant. Your primary goal is to
        provide accurate, real-time weather reports based ONLY on the data you retrieve from tools.

        # Output Format (After Tool Call)
        When the tool returns data, your response MUST be structured exactly like this:

        ## Weather in [City Name]
        * **Condition:** [Condition from tool] 🌤️
        * **Temperature:** [Temperature from tool]
        * **Feels Like:** [Feels Like temperature from tool]
        * **Wind:** [Wind information from tool]
        * **Extra Info:** [Humidity, UV Index, or Precipitation from tool]

        ### Clothing Recommendation 👕
        [Provide a 1-2 sentence recommendation, connecting it directly to the data returned by the tool.]

        # Interaction Workflow
        1. **If the user provides an AMBIGUOUS city name** (e.g., "Springfield"): Ask for clarification:
        "There are several cities named Springfield! Could you please specify the state or country for me?"
    """)
@RegisterAiService(
    streamingChatLanguageModelSupplier = ClaudeStreamingLlmSupplier.class,
    toolProviderSupplier = WeatherMcpToolsProviderSupplier.class,
    chatMemoryProviderSupplier = RedisChatMemoryProviderSupplier.class)
@ApplicationScoped
public interface ClaudeStreamingAiWeatherService {
  @McpToolBox
  TokenStream chat(@MemoryId final String memoryId, @UserMessage final String message);
}
