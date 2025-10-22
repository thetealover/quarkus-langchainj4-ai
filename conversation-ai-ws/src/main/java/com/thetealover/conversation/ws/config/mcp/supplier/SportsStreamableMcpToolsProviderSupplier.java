package com.thetealover.conversation.ws.config.mcp.supplier;

import static com.thetealover.conversation.ws.config.mcp.McpClientsConfiguration.sportsStreamableMcpClient;
import static com.thetealover.conversation.ws.config.mcp.ToolName.SPORTS_TOOLS;

import dev.langchain4j.service.tool.ToolProvider;
import io.quarkiverse.langchain4j.mcp.runtime.QuarkusMcpToolProvider;
import jakarta.inject.Singleton;
import java.util.function.Supplier;

@Singleton
public class SportsStreamableMcpToolsProviderSupplier implements Supplier<ToolProvider> {
  @Override
  public ToolProvider get() {
    return QuarkusMcpToolProvider.builder()
        .filterToolNames(SPORTS_TOOLS.getToolNames())
        .mcpClients(sportsStreamableMcpClient())
        .build();
  }

  public static ToolProvider getSportsStreamableMcpToolProvider() {
    return new SportsStreamableMcpToolsProviderSupplier().get();
  }
}
