package com.thetealover.mcp.ws.adapter.in.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thetealover.mcp.ws.adapter.out.client.f1.F1ApiClient;
import com.thetealover.mcp.ws.adapter.out.client.f1.model.DriverDto;
import com.thetealover.mcp.ws.utils.f1.F1FormatUtils;
import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonObject;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class F1Tools {
  private static final String DRIVERS_JSON_KEY = "drivers";

  private final F1ApiClient f1ApiClient;
  private final ObjectMapper objectMapper;
  private final F1FormatUtils f1FormatUtils;

  @Tool(name = "F1 driver search", description = "Search of F1 drivers")
  public String searchF1Drivers(
      @ToolArg(
              name = "F1 driver search query argument",
              description = "name or surname of an F1 driver",
              defaultValue = " ")
          final String query)
      throws JsonProcessingException {
    final JsonObject response = f1ApiClient.searchForDrivers(query);
    final List<DriverDto> drivers =
        objectMapper.readValue(response.get(DRIVERS_JSON_KEY).toString(), new TypeReference<>() {});

    log.info("Tool call: provided a list of F1 drivers. Total size: {}", drivers.size());

    return f1FormatUtils.formatDriversData(drivers);
  }
}
