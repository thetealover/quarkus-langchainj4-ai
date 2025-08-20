package com.thetealover.conversation.ws.config.ai;

import com.thetealover.conversation.ws.config.ai.qualifier.CustomOllamaStreamingChatModel;
import com.thetealover.conversation.ws.config.ai.qualifier.WeatherOllamaChatModel;
import com.thetealover.conversation.ws.config.properties.AiConfigurationProperties;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import io.quarkiverse.langchain4j.jaxrsclient.JaxRsHttpClientBuilderFactory;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.time.Duration;

@Singleton
public class OllamaChatModelConfiguration {
  private static final int CONNECT_TIMEOUT_SECONDS = 3;
  private static final int READ_TIMEOUT_SECONDS = 10;

  @Inject AiConfigurationProperties aiProperties;

  @Produces
  @Singleton
  @CustomOllamaStreamingChatModel
  public OllamaStreamingChatModel customOllamaStreamingChatModel() {
    return OllamaStreamingChatModel.builder()
        .baseUrl(aiProperties.ollamaBaseUrl())
        .httpClientBuilder(
            new JaxRsHttpClientBuilderFactory()
                .create()
                .connectTimeout(Duration.ofSeconds(CONNECT_TIMEOUT_SECONDS))
                .readTimeout(Duration.ofSeconds(READ_TIMEOUT_SECONDS)))
        .build();
  }

  @Produces
  @Singleton
  @WeatherOllamaChatModel
  public ChatModel weatherOllamaChatModel() {
    return OllamaChatModel.builder()
        .baseUrl(aiProperties.ollamaBaseUrl())
        .modelName(aiProperties.weatherAdvisor().modelId())
        .temperature(aiProperties.weatherAdvisor().temperature())
        .topK(aiProperties.weatherAdvisor().topK())
        .topP(aiProperties.weatherAdvisor().topP())
        .httpClientBuilder(
            new JaxRsHttpClientBuilderFactory()
                .create()
                .connectTimeout(Duration.ofSeconds(CONNECT_TIMEOUT_SECONDS))
                .readTimeout(Duration.ofSeconds(10)))
        .build();
  }
}
