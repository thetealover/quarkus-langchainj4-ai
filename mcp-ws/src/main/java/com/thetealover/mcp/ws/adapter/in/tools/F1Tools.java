package com.thetealover.mcp.ws.adapter.in.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thetealover.mcp.ws.adapter.out.client.f1.F1ApiClient;
import com.thetealover.mcp.ws.adapter.out.client.f1.model.DriverDto;
import com.thetealover.mcp.ws.utils.f1.F1FormatUtils;
import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import jakarta.inject.Singleton;
import jakarta.json.JsonObject;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class F1Tools {
  private static final String DRIVERS_JSON_KEY = "drivers";

  private final F1ApiClient f1ApiClient;
  private final ObjectMapper objectMapper;
  private final F1FormatUtils f1FormatUtils;

  @Tool(
      name = "search_f1_drivers",
      description =
          "Searches for Formula 1 drivers by their name and returns a list of matching drivers.")
  public String searchF1Drivers(
      @ToolArg(
              name = "query",
              description =
                  "The name or surname of the F1 driver to search for, e.g., 'Hamilton' or 'Max'.")
          final String query)
      throws JsonProcessingException {
    final JsonObject response = f1ApiClient.searchForDrivers(query);
    final List<DriverDto> drivers =
        objectMapper.readValue(response.get(DRIVERS_JSON_KEY).toString(), new TypeReference<>() {});

    log.info("Tool call: searched for F1 drivers. Total size: {}", drivers.size());
    return f1FormatUtils.formatDriversData(drivers);
  }
}
