package com.thetealover.mcp.ws.utils.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thetealover.mcp.ws.adapter.out.client.weather.model.WeatherDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class WeatherFormatUtils {
  @Inject ObjectMapper objectMapper;

  public String formatWeatherData(final WeatherDto data) {
    try {
      return objectMapper.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      log.error("Error formatting weather data", e);
      return """
                {
                    "error": "Error formatting weather data",
                    "message": "%s",
                }
            """
          .formatted(e.getMessage());
    }
  }
}
