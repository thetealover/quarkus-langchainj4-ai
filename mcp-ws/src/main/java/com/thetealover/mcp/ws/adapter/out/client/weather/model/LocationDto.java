package com.thetealover.mcp.ws.adapter.out.client.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
  private String name;
  private String region;
  private String country;
  private Double lat;
  private Double lon;

  @JsonProperty("tz_id")
  private String timezoneId;

  private String localtime;

  @JsonProperty("localtime_epoch")
  private Long localtimeEpoch;
}
