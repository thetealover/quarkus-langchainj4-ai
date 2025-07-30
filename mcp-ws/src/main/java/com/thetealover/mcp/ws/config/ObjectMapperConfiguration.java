package com.thetealover.mcp.ws.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Singleton
public class ObjectMapperConfiguration {
  @Produces
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
