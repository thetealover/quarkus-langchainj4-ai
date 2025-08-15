package com.thetealover.conversation.ws.config.ai;

import com.thetealover.conversation.ws.config.ai.qualifier.CustomOllamaStreamingChatModel;
import com.thetealover.conversation.ws.config.ai.qualifier.WeatherOllamaChatModel;
import com.thetealover.conversation.ws.config.properties.AiConfigurationProperties;
import dev.langchain4j.http.client.jdk.JdkHttpClientBuilderFactory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.time.Duration;

@Singleton
public class OllamaChatModelConfiguration {
  @Inject AiConfigurationProperties aiProperties;

  @Produces
  @Singleton
  @CustomOllamaStreamingChatModel
  public OllamaStreamingChatModel customOllamaStreamingChatModel() {
    return OllamaStreamingChatModel.builder()
        .baseUrl(aiProperties.ollamaBaseUrl())
        .httpClientBuilder(
            new JdkHttpClientBuilderFactory()
                .create()
                .connectTimeout(Duration.ofSeconds(3))
                .readTimeout(Duration.ofSeconds(10)))
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
            new JdkHttpClientBuilderFactory()
                .create()
                .connectTimeout(Duration.ofSeconds(3))
                .readTimeout(Duration.ofSeconds(10)))
        .build();
  }
}
