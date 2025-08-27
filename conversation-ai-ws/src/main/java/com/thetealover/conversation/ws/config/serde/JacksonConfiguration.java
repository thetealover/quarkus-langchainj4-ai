package com.thetealover.conversation.ws.config.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Singleton
public class JacksonConfiguration {

  @Produces
  public ObjectMapper objectMapper() {
    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    return objectMapper;
  }
}
