package com.thetealover.conversation.ws.config.mcp.supplier;

import static com.thetealover.conversation.ws.config.mcp.McpClientsConfiguration.sportsMcpClient;
import static com.thetealover.conversation.ws.config.mcp.ToolName.SPORTS_TOOLS;

import dev.langchain4j.service.tool.ToolProvider;
import io.quarkiverse.langchain4j.mcp.runtime.QuarkusMcpToolProvider;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.function.Supplier;

@ApplicationScoped
public class SportsMcpToolsProviderSupplier implements Supplier<ToolProvider> {
  @Override
  public ToolProvider get() {
    return QuarkusMcpToolProvider.builder()
        .filterToolNames(SPORTS_TOOLS.getToolNames())
        .mcpClients(sportsMcpClient())
        .build();
  }

  public static ToolProvider getSportsMcpToolProvider() {
    return new SportsMcpToolsProviderSupplier().get();
  }
}
