package com.thetealover.conversation.ws.adapter.in.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiResponseDto {
  private String response;
  private Long thinkingTimeInMillis;
}
