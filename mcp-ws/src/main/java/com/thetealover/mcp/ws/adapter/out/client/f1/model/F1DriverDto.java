package com.thetealover.mcp.ws.adapter.out.client.f1.model;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class F1DriverDto {
  private String driverId;
  private String name;
  private String surname;
  private String nationality;
  private String birthday;
  private Integer number;
  private String shortName;
  private String url;
}
