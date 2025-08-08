package com.thetealover.mcp.ws.utils.weather;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.thetealover.mcp.ws.adapter.out.client.weather.model.ConditionDto;
import com.thetealover.mcp.ws.adapter.out.client.weather.model.CurrentDto;
import com.thetealover.mcp.ws.adapter.out.client.weather.model.LocationDto;
import com.thetealover.mcp.ws.adapter.out.client.weather.model.WeatherDto;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
class WeatherFormatUtilsTest {
  @Inject WeatherFormatUtils formatUtils;

  @Test
  void formatWeatherData() {
    // 1. Arrange: Create the test data
    final LocationDto location =
        LocationDto.builder()
            .name("Yerevan")
            .region("Yerevan")
            .country("Armenia")
            .lat(40.18)
            .lon(44.51)
            .timezoneId("Asia/Yerevan")
            .localtime("2025-08-05 20:20")
            .localtimeEpoch(1754473200L)
            .build();

    final ConditionDto condition =
        ConditionDto.builder()
            .text("Clear")
            .icon("//cdn.weatherapi.com/weather/64x64/night/113.png")
            .code(1000)
            .build();

    final CurrentDto current =
        CurrentDto.builder()
            .lastUpdated("2025-08-05 20:15")
            .lastUpdatedEpoch(1754472900L)
            .tempC(22.0)
            .tempF(71.6)
            .isDay(0) // Night
            .condition(condition)
            .windMph(5.6)
            .windKph(9.0)
            .windDegree(230)
            .windDir("SW")
            .pressureMb(1012.0)
            .pressureIn(29.88)
            .precipMm(0.0)
            .precipIn(0.0)
            .humidity(45)
            .cloud(10)
            .feelslikeC(21.5)
            .feelslikeF(70.7)
            .visKm(10.0)
            .visMiles(6.0)
            .uv(1.0)
            .gustMph(8.9)
            .gustKph(14.3)
            .build();

    final WeatherDto weatherDto = new WeatherDto(location, current);

    // 2. Act: Call the method under test
    final String result = formatUtils.formatWeatherData(weatherDto);

    // 3. Assert: Check that the output matches the expected format and data
    final String expected =
        """
                Weather Report for Yerevan, Yerevan, Armenia
                ----------------------------------------------------
                Location Details:
                  - Coordinates: Latitude 40.18, Longitude 44.51
                  - Timezone: Asia/Yerevan
                  - Local Time: 2025-08-05 20:20

                Current Conditions (last updated on 2025-08-05 20:15):
                  - Weather: Clear
                  - Temperature: 22.0°C / 71.6°F
                  - Feels Like: 21.5°C / 70.7°F
                  - Wind: 9.0 kph from the SW (230°)
                  - Wind Gusts: 14.3 kph
                  - Pressure: 1012.0 mb
                  - Precipitation: 0.0 mm
                  - Humidity: 45%
                  - Cloud Cover: 10%
                  - Visibility: 10.0 km
                  - UV Index: 1.0
                """;

    assertThat(result).isEqualTo(expected);
  }
}
