package com.thetealover.mcp.ws.adapter.out.client.weather.model;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ConditionDto {
  private String text;
  private String icon;
  private Integer code;
}
