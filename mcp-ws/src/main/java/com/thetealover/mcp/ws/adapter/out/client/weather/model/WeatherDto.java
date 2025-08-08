package com.thetealover.mcp.ws.adapter.out.client.weather.model;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDto {
  private LocationDto location;
  private CurrentDto current;
}
