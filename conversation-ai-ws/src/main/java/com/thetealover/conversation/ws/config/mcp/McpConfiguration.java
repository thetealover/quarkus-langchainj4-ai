package com.thetealover.conversation.ws.config.mcp;

import static com.thetealover.conversation.ws.config.mcp.ToolName.GET_CURRENT_WEATHER_TOOL_NAME;
import static com.thetealover.conversation.ws.config.mcp.ToolName.SPORTS_TOOL_NAMES;
import static com.thetealover.conversation.ws.config.properties.McpConfigurationPropertiesProvider.getMcpConfigurationProperties;

import com.thetealover.conversation.ws.config.mcp.qualifier.SportsMcpToolProvider;
import com.thetealover.conversation.ws.config.mcp.qualifier.WeatherMcpToolProvider;
import com.thetealover.conversation.ws.config.properties.McpConfigurationPropertiesProvider.McpConfigurationProperties;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.service.tool.ToolProvider;
import io.quarkiverse.langchain4j.mcp.runtime.http.QuarkusStreamableHttpMcpTransport;
import io.vertx.core.Vertx;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class McpConfiguration {

  public static final String WEATHER_MCP_CLIENT_NAME = "weatherClient";
  public static final String SPORTS_MCP_CLIENT_NAME = "sportsClient";
  public static final String MCP_PROTOCOL_VERSION = "2025-03-26";

  @Produces
  @ApplicationScoped
  @WeatherMcpToolProvider
  public ToolProvider weatherMcpToolProvider() {
    log.info("Initializing MCP tool provider for weather tools");

    return McpToolProvider.builder()
        .mcpClients(weatherMcpClient())
        .filterToolNames(GET_CURRENT_WEATHER_TOOL_NAME)
        .build();
  }

  @Produces
  @ApplicationScoped
  @SportsMcpToolProvider
  public ToolProvider sportsMcpToolProvider() {
    log.info("Initializing MCP tool provider for sports tools");

    return McpToolProvider.builder()
        .mcpClients(sportsMcpClient())
        .filterToolNames(SPORTS_TOOL_NAMES)
        .build();
  }

  private McpClient weatherMcpClient() {
    log.info("Initializing MCP client: {}", WEATHER_MCP_CLIENT_NAME);

    return new DefaultMcpClient.Builder()
        .clientName(WEATHER_MCP_CLIENT_NAME)
        // .protocolVersion("2024-11-05")
        .protocolVersion(MCP_PROTOCOL_VERSION)
        .key(WEATHER_MCP_CLIENT_NAME)
        .transport(mcpTransport())
        .build();
  }

  public McpClient sportsMcpClient() {
    log.info("Initializing MCP client: {}", SPORTS_MCP_CLIENT_NAME);

    return new DefaultMcpClient.Builder()
        .clientName(SPORTS_MCP_CLIENT_NAME)
        // .protocolVersion("2024-11-05")
        .protocolVersion(MCP_PROTOCOL_VERSION)
        .key(SPORTS_MCP_CLIENT_NAME)
        .transport(mcpTransport())
        .build();
  }

  private McpTransport mcpTransport() {
    final McpConfigurationProperties mcpProperties = getMcpConfigurationProperties();
    final String baseUrl = mcpProperties.getBaseUrl();
    final Boolean logRequests = mcpProperties.getLogRequests();
    final Boolean logResponses = mcpProperties.getLogResponses();
    final Duration timeoutInSeconds = mcpProperties.getTimeoutInSeconds();

    log.info(
        "Initializing MCP transport: Base URL={}, timeout in seconds={}, log requests={}, log responses={},",
        baseUrl,
        timeoutInSeconds,
        logRequests,
        logResponses);

    return new QuarkusStreamableHttpMcpTransport.Builder()
        .url(baseUrl)
        .logRequests(logRequests)
        .logResponses(logResponses)
        .timeout(timeoutInSeconds)
        .httpClient(Vertx.vertx().createHttpClient())
        .build();
  }
}
