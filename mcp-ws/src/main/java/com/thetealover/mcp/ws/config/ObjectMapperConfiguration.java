package com.thetealover.mcp.ws.config;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Singleton
public class ObjectMapperConfiguration {
  @Produces
  public ObjectMapper objectMapper() {
    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setPropertyNamingStrategy(SNAKE_CASE);

    return objectMapper;
  }
}
