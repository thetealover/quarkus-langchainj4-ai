package com.thetealover.conversation.ws.config.serde;

import static dev.langchain4j.data.message.JacksonChatMessageJsonCodec.chatMessageJsonMapperBuilder;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageJsonCodec;
import jakarta.inject.Singleton;
import java.util.List;

@Singleton
public class CustomChatMessageCodec implements ChatMessageJsonCodec {
  private static final TypeReference<List<ChatMessage>> MESSAGE_LIST_TYPE =
      new TypeReference<>() {};

  private static final ObjectMapper LANGCHAIN_OBJECT_MAPPER =
      chatMessageJsonMapperBuilder().build();

  @Override
  public ChatMessage messageFromJson(String json) {
    try {
      return LANGCHAIN_OBJECT_MAPPER.readValue(json, ChatMessage.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<ChatMessage> messagesFromJson(String json) {
    if (isNull(json)) {
      return emptyList();
    }

    try {
      return LANGCHAIN_OBJECT_MAPPER.readValue(json, MESSAGE_LIST_TYPE);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String messageToJson(ChatMessage message) {
    try {
      return LANGCHAIN_OBJECT_MAPPER.writeValueAsString(message);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String messagesToJson(List<ChatMessage> messages) {
    try {
      return LANGCHAIN_OBJECT_MAPPER.writeValueAsString(messages);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
