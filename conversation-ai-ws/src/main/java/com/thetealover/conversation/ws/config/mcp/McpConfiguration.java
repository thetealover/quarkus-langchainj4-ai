package com.thetealover.conversation.ws.config.mcp;

import static java.time.temporal.ChronoUnit.SECONDS;

import com.thetealover.conversation.ws.config.mcp.qualifier.WeatherMcpClient;
import com.thetealover.conversation.ws.config.mcp.qualifier.WeatherMcpToolProvider;
import com.thetealover.conversation.ws.config.properties.McpConfigurationProperties;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.service.tool.ToolProvider;
import io.quarkiverse.langchain4j.mcp.runtime.http.QuarkusHttpMcpTransport;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class McpConfiguration {
  @Inject McpConfigurationProperties properties;

  @Produces
  @Singleton
  public McpTransport mcpTransport() {
    final String baseUrl = properties.baseUrl();
    final Boolean logRequests = properties.logRequests();
    final Boolean logResponses = properties.logResponses();
    final Long timeoutInSeconds = properties.timeoutInSeconds();

    log.info(
        "Initializing MCP transport: Base SSE URL={}, timout in seconds={}, log requests={}, log responses={},",
        baseUrl,
        timeoutInSeconds,
        logRequests,
        logResponses);

    return new QuarkusHttpMcpTransport.Builder()
        .sseUrl(baseUrl)
        .logRequests(logRequests)
        .logResponses(logResponses)
        .timeout(Duration.of(timeoutInSeconds, SECONDS))
        .build();
  }

  @Produces
  @Singleton
  @WeatherMcpClient
  public McpClient weatherMcpClient() {
    return new DefaultMcpClient.Builder()
        .clientName("weatherClient")
        .protocolVersion("2024-11-05")
        .key("weatherMcpClient")
        .transport(mcpTransport())
        .build();
  }

  @Produces
  @Singleton
  @WeatherMcpToolProvider
  public ToolProvider weatherMcpToolProvider() {
    return McpToolProvider.builder()
        .mcpClients(weatherMcpClient())
        .filterToolNames("get_current_weather_for_a_city")
        .build();
  }
}
