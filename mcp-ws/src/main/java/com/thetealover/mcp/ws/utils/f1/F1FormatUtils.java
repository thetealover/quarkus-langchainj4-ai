package com.thetealover.mcp.ws.utils.f1;

import com.thetealover.mcp.ws.adapter.out.client.f1.model.DriverDto;
import io.quarkus.qute.Qute;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class F1FormatUtils {

  public String formatDriversData(final List<DriverDto> drivers) {
    return drivers.stream()
        .map(
            driver ->
                Qute.fmt(
                    """
            Name: {d.name} {d.surname}
            Nationality: {d.nationality}
            Birthday: {d.birthday}
            Number: {d.number}
            Short Name: {d.shortName}
            Wikipedia URL: {d.url}
            """,
                    Map.of("d", driver)))
        .collect(Collectors.joining("---\n"));
  }
}
