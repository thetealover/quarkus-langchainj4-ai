package com.thetealover.conversation.ws.config.ai.modelsupplier.ollama;

import static com.thetealover.conversation.ws.config.properties.AiConfigurationPropertiesProvider.getLocalOllamaProperties;

import com.thetealover.conversation.ws.config.properties.AiConfigurationPropertiesProvider.AiConfiguration;
import dev.langchain4j.http.client.HttpClientBuilderLoader;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import java.util.function.Supplier;

public class OllamaStreamingLlmSupplier implements Supplier<StreamingChatModel> {

  @Override
  public StreamingChatModel get() {
    final AiConfiguration localOllamaProperties = getLocalOllamaProperties();

    return OllamaStreamingChatModel.builder()
        .baseUrl(localOllamaProperties.getBaseUrl())
        .modelName(localOllamaProperties.getModelId())
        .temperature(localOllamaProperties.getTemperature())
        .logRequests(localOllamaProperties.getLogRequests())
        .logResponses(localOllamaProperties.getLogResponses())
        .httpClientBuilder(HttpClientBuilderLoader.loadHttpClientBuilder())
        .build();
  }

  public static StreamingChatModel getOllamaChatModel() {
    return new OllamaStreamingLlmSupplier().get();
  }
}
