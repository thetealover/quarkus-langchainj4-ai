package com.thetealover.conversation.ws.adapter.in.websocket;

import com.thetealover.conversation.ws.service.ai.weather.OllamaAiWeatherAdvisingService;
import com.thetealover.conversation.ws.service.ai.weather.OllamaAiWeatherService;
import io.quarkus.websockets.next.*;
import lombok.RequiredArgsConstructor;

@WebSocket(path = "weather-ai")
@RequiredArgsConstructor
public class WeatherAiWebSocket {
  // todo to be enhanced - MCP calls
  private final OllamaAiWeatherAdvisingService ollamaAiWeatherAdvisingService;
  private final OllamaAiWeatherService ollamaAiWeatherService;

  @OnOpen
  public String onOpen() {
    return "Hi, I can help you with the choice of clothing based on the weather! Please, provide me with the City you're in.";
  }

  @OnTextMessage
  public String onTextMessage(final String message) {
    return ollamaAiWeatherService.chat(message);
  }
}
