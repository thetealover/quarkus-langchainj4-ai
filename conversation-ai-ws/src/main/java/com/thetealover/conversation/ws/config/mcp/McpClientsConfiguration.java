package com.thetealover.conversation.ws.config.mcp;

import static com.thetealover.conversation.ws.config.properties.McpConfigurationPropertiesProvider.getSseMcpConfigurationProperties;
import static com.thetealover.conversation.ws.config.properties.McpConfigurationPropertiesProvider.getStreamableMcpConfigurationProperties;

import com.thetealover.conversation.ws.config.properties.McpConfigurationPropertiesProvider.McpConfigurationProperties;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import io.quarkiverse.langchain4j.mcp.runtime.http.QuarkusHttpMcpTransport;
import io.quarkiverse.langchain4j.mcp.runtime.http.QuarkusStreamableHttpMcpTransport;
import io.vertx.core.Vertx;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class McpClientsConfiguration {

  public static final String WEATHER_MCP_CLIENT_NAME = "weatherClient";
  public static final String SPORTS_STREAMING_MCP_CLIENT_NAME = "sportsStreamingMcpClient";
  public static final String SPORTS_SSE_MCP_CLIENT_NAME = "sportsSseMcpClient";
  public static final String MCP_PROTOCOL_VERSION = "2025-06-18";

  public static McpClient weatherMcpClient() {
    return new DefaultMcpClient.Builder()
        .clientName(WEATHER_MCP_CLIENT_NAME)
        // .protocolVersion("2024-11-05")
        .protocolVersion(MCP_PROTOCOL_VERSION)
        .key(WEATHER_MCP_CLIENT_NAME)
        .transport(streamableMcpTransport())
        .build();
  }

  public static McpClient sportsStreamableMcpClient() {
    log.info("Creating Sports Streaming MCP Client");

    return new DefaultMcpClient.Builder()
        .clientName(SPORTS_STREAMING_MCP_CLIENT_NAME)
        // .protocolVersion("2024-11-05")
        .protocolVersion(MCP_PROTOCOL_VERSION)
        .key(SPORTS_STREAMING_MCP_CLIENT_NAME)
        .transport(streamableMcpTransport())
        .build();
  }

  public static McpClient sportsSseMcpClient() {
    log.info("Creating Sports SSE MCP Client");

    return new DefaultMcpClient.Builder()
        .clientName(SPORTS_SSE_MCP_CLIENT_NAME)
        //        .protocolVersion("2024-11-05")
        .protocolVersion(MCP_PROTOCOL_VERSION)
        .key(SPORTS_SSE_MCP_CLIENT_NAME)
        .transport(blockingMcpTransport())
        .build();
  }

  private static McpTransport streamableMcpTransport() {
    final McpConfigurationProperties mcpProperties = getStreamableMcpConfigurationProperties();

    return new QuarkusStreamableHttpMcpTransport.Builder()
        .url(mcpProperties.getBaseUrl())
        .logRequests(mcpProperties.getLogRequests())
        .logResponses(mcpProperties.getLogResponses())
        .timeout(mcpProperties.getTimeoutInSeconds())
        .httpClient(Vertx.vertx().createHttpClient())
        .build();
  }

  private static McpTransport blockingMcpTransport() {
    final McpConfigurationProperties mcpProperties = getSseMcpConfigurationProperties();

    return new QuarkusHttpMcpTransport.Builder()
        .sseUrl(mcpProperties.getBaseUrl())
        .logRequests(mcpProperties.getLogRequests())
        .logResponses(mcpProperties.getLogResponses())
        .timeout(mcpProperties.getTimeoutInSeconds())
        .build();
  }
}
