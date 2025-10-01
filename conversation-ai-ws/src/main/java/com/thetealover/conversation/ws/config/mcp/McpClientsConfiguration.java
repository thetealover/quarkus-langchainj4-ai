package com.thetealover.conversation.ws.config.mcp;

import static com.thetealover.conversation.ws.config.properties.McpConfigurationPropertiesProvider.getMcpConfigurationProperties;

import com.thetealover.conversation.ws.config.properties.McpConfigurationPropertiesProvider.McpConfigurationProperties;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import io.quarkiverse.langchain4j.mcp.runtime.http.QuarkusStreamableHttpMcpTransport;
import io.vertx.core.Vertx;
import jakarta.inject.Singleton;

@Singleton
public class McpClientsConfiguration {

  public static final String WEATHER_MCP_CLIENT_NAME = "weatherClient";
  public static final String SPORTS_MCP_CLIENT_NAME = "sportsClient";
  public static final String MCP_PROTOCOL_VERSION = "2025-03-26";

  public static McpClient weatherMcpClient() {
    return new DefaultMcpClient.Builder()
        .clientName(WEATHER_MCP_CLIENT_NAME)
        // .protocolVersion("2024-11-05")
        .protocolVersion(MCP_PROTOCOL_VERSION)
        .key(WEATHER_MCP_CLIENT_NAME)
        .transport(mcpTransport())
        .build();
  }

  public static McpClient sportsMcpClient() {
    return new DefaultMcpClient.Builder()
        .clientName(SPORTS_MCP_CLIENT_NAME)
        // .protocolVersion("2024-11-05")
        .protocolVersion(MCP_PROTOCOL_VERSION)
        .key(SPORTS_MCP_CLIENT_NAME)
        .transport(mcpTransport())
        .build();
  }

  private static McpTransport mcpTransport() {
    final McpConfigurationProperties mcpProperties = getMcpConfigurationProperties();

    return new QuarkusStreamableHttpMcpTransport.Builder()
        .url(mcpProperties.getBaseUrl())
        .logRequests(mcpProperties.getLogRequests())
        .logResponses(mcpProperties.getLogResponses())
        .timeout(mcpProperties.getTimeoutInSeconds())
        .httpClient(Vertx.vertx().createHttpClient())
        .build();
  }
}
