package com.thetealover.conversation.ws.adapter.in.api.model.ai;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiRequestDto {
  @NotBlank private String userId;
  @NotBlank private String message;
}
