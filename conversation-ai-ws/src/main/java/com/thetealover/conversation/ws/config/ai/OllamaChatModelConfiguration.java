package com.thetealover.conversation.ws.config.ai;

import static com.thetealover.conversation.ws.config.properties.AiConfigurationPropertiesProvider.getLocalOllamaProperties;

import com.thetealover.conversation.ws.config.ai.qualifier.chatmodel.CustomOllamaStreamingChatModel;
import com.thetealover.conversation.ws.config.ai.qualifier.chatmodel.WeatherOllamaChatModel;
import com.thetealover.conversation.ws.config.properties.AiConfigurationPropertiesProvider.AiConfiguration;
import dev.langchain4j.http.client.HttpClientBuilderLoader;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Singleton
public class OllamaChatModelConfiguration {

  @Produces
  @Singleton
  @CustomOllamaStreamingChatModel
  public StreamingChatModel customOllamaStreamingChatModel() {
    final AiConfiguration localOllamaProperties = getLocalOllamaProperties();

    return OllamaStreamingChatModel.builder()
        .baseUrl(localOllamaProperties.getBaseUrl())
        .httpClientBuilder(HttpClientBuilderLoader.loadHttpClientBuilder())
        .build();
  }

  @Produces
  @Singleton
  @WeatherOllamaChatModel
  public ChatModel weatherOllamaChatModel() {
    final AiConfiguration localOllamaProperties = getLocalOllamaProperties();

    return OllamaChatModel.builder()
        .baseUrl(localOllamaProperties.getBaseUrl())
        .modelName(localOllamaProperties.getModelId())
        .temperature(localOllamaProperties.getTemperature())
        .topK(localOllamaProperties.getTopK())
        .topP(localOllamaProperties.getTopP())
        .httpClientBuilder(HttpClientBuilderLoader.loadHttpClientBuilder())
        .build();
  }
}
