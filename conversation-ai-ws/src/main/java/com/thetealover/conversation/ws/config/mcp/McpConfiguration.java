package com.thetealover.conversation.ws.config.mcp;

import static com.thetealover.conversation.ws.config.properties.McpConfigurationPropertiesProvider.getMcpConfigurationProperties;

import com.thetealover.conversation.ws.config.mcp.qualifier.WeatherMcpToolProvider;
import com.thetealover.conversation.ws.config.properties.McpConfigurationPropertiesProvider.McpConfigurationProperties;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.service.tool.ToolProvider;
import io.quarkiverse.langchain4j.mcp.runtime.http.QuarkusHttpMcpTransport;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class McpConfiguration {

  @Produces
  @ApplicationScoped
  @WeatherMcpToolProvider
  public ToolProvider weatherMcpToolProvider() {
    return McpToolProvider.builder()
        .mcpClients(weatherMcpClient())
        .filterToolNames("get_current_weather")
        .build();
  }

  private McpTransport mcpTransport() {
    final McpConfigurationProperties mcpProperties = getMcpConfigurationProperties();
    final String baseUrl = mcpProperties.getBaseUrl();
    final Boolean logRequests = mcpProperties.getLogRequests();
    final Boolean logResponses = mcpProperties.getLogResponses();
    final Duration timeoutInSeconds = mcpProperties.getTimeoutInSeconds();

    log.info(
        "Initializing MCP transport: Base SSE URL={}, timeout in seconds={}, log requests={}, log responses={},",
        baseUrl,
        timeoutInSeconds,
        logRequests,
        logResponses);

    return new QuarkusHttpMcpTransport.Builder()
        .sseUrl(baseUrl)
        .logRequests(logRequests)
        .logResponses(logResponses)
        .timeout(timeoutInSeconds)
        .build();
  }

  private McpClient weatherMcpClient() {
    return new DefaultMcpClient.Builder()
        .clientName("weatherClient")
        .protocolVersion("2024-11-05")
        .key("weatherMcpClient")
        .transport(mcpTransport())
        .build();
  }
}
