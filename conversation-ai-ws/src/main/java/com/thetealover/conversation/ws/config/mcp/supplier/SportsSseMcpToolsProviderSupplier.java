package com.thetealover.conversation.ws.config.mcp.supplier;

import static com.thetealover.conversation.ws.config.mcp.McpClientsConfiguration.sportsSseMcpClient;
import static com.thetealover.conversation.ws.config.mcp.ToolName.SPORTS_TOOLS;

import dev.langchain4j.service.tool.ToolProvider;
import io.quarkiverse.langchain4j.mcp.runtime.QuarkusMcpToolProvider;
import jakarta.inject.Singleton;
import java.util.function.Supplier;

@Singleton
public class SportsSseMcpToolsProviderSupplier implements Supplier<ToolProvider> {
  @Override
  public ToolProvider get() {
    return QuarkusMcpToolProvider.builder()
        .filterToolNames(SPORTS_TOOLS.getToolNames())
        .mcpClients(sportsSseMcpClient())
        .build();
  }

  public static ToolProvider getSportsSseMcpToolProvider() {
    return new SportsSseMcpToolsProviderSupplier().get();
  }
}
