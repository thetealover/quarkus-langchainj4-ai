package com.thetealover.conversation.ws.adapter.in.websocket;

import com.thetealover.conversation.ws.service.ai.weather.OllamaAiWeatherService;
import io.quarkus.websockets.next.*;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebSocket(path = "weather-ai")
public class WeatherAiWebSocket {
  @Inject OllamaAiWeatherService ollamaAiWeatherService;

  @OnOpen
  public String onOpen() {
    return "Hi, I can help you with the choice of clothing based on the weather! Please, provide me with the City you're in.";
  }

  @OnTextMessage
  public String onTextMessage(final String message) {
    log.info("Received message: {}", message);

    return ollamaAiWeatherService.chat(message);
  }
}
