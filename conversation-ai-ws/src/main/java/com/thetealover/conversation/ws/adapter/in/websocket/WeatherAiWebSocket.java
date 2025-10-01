package com.thetealover.conversation.ws.adapter.in.websocket;

import com.thetealover.conversation.ws.service.ai.quarkus.OllamaBlockingAiWeatherService;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.inject.Inject;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebSocket(path = "blocking/weather-ai")
public class WeatherAiWebSocket {
  @Inject OllamaBlockingAiWeatherService ollamaBlockingAiWeatherService;
  @Inject ChatMemoryStore memoryStore;

  @OnOpen
  public String onOpen() {
    return "Hi, I can help you with the choice of clothing based on the weather! Please, provide me with the City you're in.";
  }

  @OnTextMessage
  public String onTextMessage(final String message, final WebSocketConnection connection) {
    final String connectionId = connection.id();
    log.info("Received message: {}. Connection ID: {}", message, connectionId);

    final List<ChatMessage> messages = memoryStore.getMessages(connectionId);
    log.info(
        "Total messages count in memory for Connection ID {}: {}", connectionId, messages.size());

    return ollamaBlockingAiWeatherService.chat(connectionId, message);
  }
}
