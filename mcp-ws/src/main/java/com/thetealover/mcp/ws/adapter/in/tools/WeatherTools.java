package com.thetealover.mcp.ws.adapter.in.tools;

import com.thetealover.mcp.ws.adapter.out.client.weather.WeatherApiV1Client;
import com.thetealover.mcp.ws.adapter.out.client.weather.model.WeatherDto;
import com.thetealover.mcp.ws.config.McpWsConfigurationProperties;
import com.thetealover.mcp.ws.utils.weather.WeatherFormatUtils;
import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class WeatherTools {
  private final McpWsConfigurationProperties properties;
  private final WeatherApiV1Client weatherApiV1Client;
  private final WeatherFormatUtils weatherFormatUtils;

  @Tool(name = "weather", description = "Get the current weather information for a city")
  public String getCurrentWeather(
      @ToolArg(name = "Location", description = "city name", defaultValue = "London")
          final String location) {
    final WeatherDto currentWeather =
        weatherApiV1Client.getCurrentWeather(location, properties.weatherApiKey());

    log.info("Tool call: provided a location. Location: {}", location);
    return weatherFormatUtils.formatWeatherData(currentWeather);
  }
}
