package com.thetealover.mcp.ws.config;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thetealover.mcp.ws.config.qualifier.ReadingObjectMapper;
import com.thetealover.mcp.ws.config.qualifier.WritingObjectMapper;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Singleton
public class ObjectMapperConfiguration {
  /**
   * Object mapper for writing JSON responses with snake_case naming strategy. Recommended for MCP
   * JSON response conversions since LLM models prefer snake_case field names.
   *
   * @return ObjectMapper with snake_case naming strategy.
   */
  @Produces
  @WritingObjectMapper
  public ObjectMapper writingObjectMapper() {
    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setPropertyNamingStrategy(SNAKE_CASE);

    return objectMapper;
  }

  @Produces
  @ReadingObjectMapper
  public ObjectMapper readingObjectMapper() {
    return new ObjectMapper();
  }
}
