package com.thetealover.conversation.ws.service.ai.weather;

import com.thetealover.conversation.ws.config.ai.qualifier.WeatherBlockingAiService;
import com.thetealover.conversation.ws.service.common.BlockingAiService;
import dev.langchain4j.service.Result;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
public class OllamaAiWeatherAdvisingService {
  @Inject @WeatherBlockingAiService BlockingAiService aiService;

  public Result<String> chat(final String message) {
    return aiService.chatForResponse(List.of(message));
  }
}
