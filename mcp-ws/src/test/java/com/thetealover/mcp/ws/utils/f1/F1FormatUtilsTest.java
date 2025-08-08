package com.thetealover.mcp.ws.utils.f1;

import static org.assertj.core.api.Assertions.assertThat;

import com.thetealover.mcp.ws.adapter.out.client.f1.model.DriverDto;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.util.List;
import org.junit.jupiter.api.Test;

@QuarkusTest
class F1FormatUtilsTest {
  @Inject F1FormatUtils formatUtils;

  @Test
  void formatSingleDriverData() {
    final DriverDto driverDto =
        DriverDto.builder()
            .driverId("max_verstappen")
            .name("Max")
            .surname("Verstappen")
            .nationality("Netherlands")
            .birthday("30/09/1997")
            .number(33)
            .shortName("VER")
            .url("https://en.wikipedia.org/wiki/Max_Verstappen")
            .build();

    final String result = formatUtils.formatDriversData(List.of(driverDto));

    assertThat(result)
        .isEqualTo(
            """
Name: Max Verstappen
Nationality: Netherlands
Birthday: 30/09/1997
Number: 33
Short Name: VER
Wikipedia URL: https://en.wikipedia.org/wiki/Max_Verstappen
        """);
  }

  @Test
  void formatMultipleDriverData() {
    final DriverDto max =
        DriverDto.builder()
            .driverId("max_verstappen")
            .name("Max")
            .surname("Verstappen")
            .nationality("Netherlands")
            .birthday("30/09/1997")
            .number(33)
            .shortName("VER")
            .url("https://en.wikipedia.org/wiki/Max_Verstappen")
            .build();

    final DriverDto lewis =
        DriverDto.builder()
            .driverId("lewis_hamilton")
            .name("Lewis")
            .surname("Hamilton")
            .nationality("United Kingdom")
            .birthday("07/01/1985")
            .number(44)
            .shortName("HAM")
            .url("https://en.wikipedia.org/wiki/Lewis_Hamilton")
            .build();

    final String result = formatUtils.formatDriversData(List.of(max, lewis));

    assertThat(result)
        .isEqualTo(
            """
Name: Max Verstappen
Nationality: Netherlands
Birthday: 30/09/1997
Number: 33
Short Name: VER
Wikipedia URL: https://en.wikipedia.org/wiki/Max_Verstappen
---
Name: Lewis Hamilton
Nationality: United Kingdom
Birthday: 07/01/1985
Number: 44
Short Name: HAM
Wikipedia URL: https://en.wikipedia.org/wiki/Lewis_Hamilton
""");
  }
}
