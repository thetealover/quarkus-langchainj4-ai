package com.thetealover.conversation.ws.config.ai;

import com.thetealover.conversation.ws.config.ai.qualifier.CustomOllamaStreamingChatModel;
import com.thetealover.conversation.ws.config.properties.AiConfigurationProperties;
import dev.langchain4j.http.client.jdk.JdkHttpClientBuilderFactory;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.time.Duration;

@Singleton
public class OllamaChatModelConfiguration {
  @Inject AiConfigurationProperties aiProperties;

  @Produces
  @CustomOllamaStreamingChatModel
  public OllamaStreamingChatModel customOllamaChatModel() {
    return OllamaStreamingChatModel.builder()
        .baseUrl(aiProperties.ollamaBaseUrl())
        .httpClientBuilder(
            new JdkHttpClientBuilderFactory()
                .create()
                .connectTimeout(Duration.ofSeconds(3))
                .readTimeout(Duration.ofSeconds(10)))
        .build();
  }
}
