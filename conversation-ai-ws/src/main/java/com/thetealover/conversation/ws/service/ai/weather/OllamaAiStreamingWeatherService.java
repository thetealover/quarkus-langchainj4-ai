package com.thetealover.conversation.ws.service.ai.weather;

import com.thetealover.conversation.ws.config.ai.qualifier.CustomOllamaStreamingChatModel;
import com.thetealover.conversation.ws.config.properties.AiConfigurationProperties;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class OllamaAiStreamingWeatherService {
  private static final SystemMessage SYSTEM_MESSAGE =
      SystemMessage.from(
          """
                  You're an assistant with an extensive weather knowledge base.
                  Your job is to provide information about the weather based on the user's location
                  (most probably the City) and give recommendations on clothing the user should wear.
                  Your answers should be concise, informative, and helpful.
                  Provide information about what type of clothing the user would need based on the hours of the day and the weather conditions.
                  If the user asks about the weather without providing a specific location, ask them to specify a location.
                  If the user asks irrelevan questions, politely remind them that you're here to provide weather information and recommendations on clothing.
                  """);

  @Inject AiConfigurationProperties properties;
  @Inject @CustomOllamaStreamingChatModel OllamaStreamingChatModel ollamaStreamingChatModel;

  // todo to be enhanced - tools are not executed yet, a ToolExecutionProvider should be implemented
  public Multi<String> chat(final String message) {
    final UserMessage userMessage =
        UserMessage.builder().contents(List.of(TextContent.from(message))).build();

    return Multi.createFrom()
        .emitter(
            multiEmitter ->
                ollamaStreamingChatModel.chat(
                    ChatRequest.builder()
                        .modelName(properties.weatherAdvisor().modelId())
                        .messages(List.of(SYSTEM_MESSAGE, userMessage))
                        .temperature(properties.weatherAdvisor().temperature())
                        .maxOutputTokens(properties.weatherAdvisor().maxOutputTokens())
                        .toolSpecifications(
                            ToolSpecification.builder()
                                .name("weather")
                                .description("Get the current weather information for a city")
                                .parameters(
                                    JsonObjectSchema.builder()
                                        .addStringProperty("Location", "city name")
                                        .build())
                                .build())
                        .build(),
                    new StreamingChatResponseHandler() {
                      @Override
                      public void onPartialResponse(String partialResponse) {
                        multiEmitter.emit(partialResponse);
                      }

                      @Override
                      public void onCompleteResponse(ChatResponse completeResponse) {
                        multiEmitter.complete();
                        log.info("OnComplete response: {}", completeResponse);
                      }

                      @Override
                      public void onError(Throwable error) {
                        multiEmitter.fail(error);
                        log.info("Error: {}", error.getMessage());
                      }
                    }));
  }
}
