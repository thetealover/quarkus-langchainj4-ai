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
public class CurrentDto {
  private ConditionDto condition;

  @JsonProperty("last_updated_epoch")
  private Long lastUpdatedEpoch;

  @JsonProperty("last_updated")
  private String lastUpdated;

  @JsonProperty("temp_c")
  private Double tempC;

  @JsonProperty("temp_f")
  private Double tempF;

  @JsonProperty("is_day")
  private Integer isDay;

  @JsonProperty("wind_mph")
  private Double windMph;

  @JsonProperty("wind_kph")
  private Double windKph;

  @JsonProperty("wind_degree")
  private Integer windDegree;

  @JsonProperty("wind_dir")
  private String windDir;

  @JsonProperty("pressure_mb")
  private Double pressureMb;

  @JsonProperty("pressure_in")
  private Double pressureIn;

  @JsonProperty("precip_mm")
  private Double precipMm;

  @JsonProperty("precip_in")
  private Double precipIn;

  private Integer humidity;
  private Integer cloud;

  @JsonProperty("feelslike_c")
  private Double feelslikeC;

  @JsonProperty("feelslike_f")
  private Double feelslikeF;

  @JsonProperty("windchill_c")
  private Double windchillC;

  @JsonProperty("windchill_f")
  private Double windchillF;

  @JsonProperty("heatindex_c")
  private Double heatindexC;

  @JsonProperty("heatindex_f")
  private Double heatindexF;

  @JsonProperty("dewpoint_c")
  private Double dewpointC;

  @JsonProperty("dewpoint_f")
  private Double dewpointF;

  @JsonProperty("vis_km")
  private Double visKm;

  @JsonProperty("vis_miles")
  private Double visMiles;

  private Double uv;

  @JsonProperty("gust_mph")
  private Double gustMph;

  @JsonProperty("gust_kph")
  private Double gustKph;

  @JsonProperty("short_rad")
  private Double shortRad;

  @JsonProperty("diff_rad")
  private Double diffRad;

  private Double dni;
  private Double gti;
}
