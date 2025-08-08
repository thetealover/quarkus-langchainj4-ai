package com.thetealover.mcp.ws.utils.weather;

import com.thetealover.mcp.ws.adapter.out.client.weather.model.WeatherDto;
import io.quarkus.qute.Qute;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WeatherFormatUtils {

  public String formatWeatherData(final WeatherDto weather) {
    String format =
        """
                Weather Report for {data.location.name}, {data.location.region}, {data.location.country}
                ----------------------------------------------------
                Location Details:
                  - Coordinates: Latitude {data.location.lat}, Longitude {data.location.lon}
                  - Timezone: {data.location.timezoneId}
                  - Local Time: {data.location.localtime}

                Current Conditions (last updated on {data.current.lastUpdated}):
                  - Weather: {data.current.condition.text}
                  - Temperature: {data.current.tempC}°C / {data.current.tempF}°F
                  - Feels Like: {data.current.feelslikeC}°C / {data.current.feelslikeF}°F
                  - Wind: {data.current.windKph} kph from the {data.current.windDir} ({data.current.windDegree}°)
                  - Wind Gusts: {data.current.gustKph} kph
                  - Pressure: {data.current.pressureMb} mb
                  - Precipitation: {data.current.precipMm} mm
                  - Humidity: {data.current.humidity}%
                  - Cloud Cover: {data.current.cloud}%
                  - Visibility: {data.current.visKm} km
                  - UV Index: {data.current.uv}
                """;

    return Qute.fmt(format).data("data", weather).render();
  }
}
