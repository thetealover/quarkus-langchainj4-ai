package com.thetealover.conversation.ws.service.ai.common.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AiResponseCompletedEvent {
  private final String chatId;
  private final String response;
}
