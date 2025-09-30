package com.thetealover.conversation.ws.config.ai.modelsupplier.ollama;

import static com.thetealover.conversation.ws.config.properties.AiConfigurationPropertiesProvider.getLocalOllamaProperties;

import com.thetealover.conversation.ws.config.properties.AiConfigurationPropertiesProvider.AiConfiguration;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import java.util.function.Supplier;

public class OllamaBlockingLlmSupplier implements Supplier<ChatModel> {

  @Override
  public ChatModel get() {
    final AiConfiguration localOllamaProperties = getLocalOllamaProperties();

    return OllamaChatModel.builder()
        .baseUrl(localOllamaProperties.getBaseUrl())
        .modelName(localOllamaProperties.getModelId())
        .temperature(localOllamaProperties.getTemperature())
        .logRequests(localOllamaProperties.getLogRequests())
        .logResponses(localOllamaProperties.getLogResponses())
        .build();
  }

  public static ChatModel getOllamaChatModel() {
    return new OllamaBlockingLlmSupplier().get();
  }
}
