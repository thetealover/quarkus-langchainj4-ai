package com.thetealover.mcp.ws.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "ws")
public interface McpWsConfigurationProperties {
  String appVersion();

  String weatherApiKey();
}
