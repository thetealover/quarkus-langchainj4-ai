package com.thetealover.conversation.ws.adapter.in.websocket;

import com.thetealover.conversation.ws.config.ai.qualifier.WeatherBlockingAiService;
import com.thetealover.conversation.ws.service.common.BlockingAiService;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebSocket(path = "imperative/weather-ai")
public class WeatherAiImperativeWebSocket {
  @Inject @WeatherBlockingAiService BlockingAiService weatherBlockingAiService;

  @OnOpen
  public String onOpen() {
    return "Hi, I can help you with the choice of clothing based on the weather! Please, provide me with the City you're in.";
  }

  @OnTextMessage
  public String onTextMessage(final String message) {
    log.info("Received message: {}", message);

    return weatherBlockingAiService.chat(message);
  }
}
