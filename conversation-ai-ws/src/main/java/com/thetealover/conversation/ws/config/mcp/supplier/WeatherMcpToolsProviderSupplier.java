package com.thetealover.conversation.ws.config.mcp.supplier;

import static com.thetealover.conversation.ws.config.mcp.McpClientsConfiguration.weatherMcpClient;
import static com.thetealover.conversation.ws.config.mcp.ToolName.WEATHER_TOOLS;

import dev.langchain4j.service.tool.ToolProvider;
import io.quarkiverse.langchain4j.mcp.runtime.QuarkusMcpToolProvider;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.function.Supplier;

@ApplicationScoped
public class WeatherMcpToolsProviderSupplier implements Supplier<ToolProvider> {
  @Override
  public ToolProvider get() {
    return QuarkusMcpToolProvider.builder()
        .filterToolNames(WEATHER_TOOLS.getToolNames())
        .mcpClients(weatherMcpClient())
        .build();
  }

  public static ToolProvider getWeatherMcpToolProvider() {
    return new WeatherMcpToolsProviderSupplier().get();
  }
}
