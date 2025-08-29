package com.thetealover.mcp.ws.utils.f1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thetealover.mcp.ws.adapter.out.client.f1.model.F1DriverDto;
import com.thetealover.mcp.ws.config.qualifier.WritingObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class F1FormatUtils {
  @WritingObjectMapper @Inject ObjectMapper writingObjectMapper;

  public String formatDriversData(final List<F1DriverDto> data) {
    try {
      return writingObjectMapper.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      log.error("Error formatting drivers data", e);
      return """
                {
                    "error": "Failed to process drivers data",
                    "message": "%s"
                }
            """
          .formatted(e.getMessage());
    }
  }
}
