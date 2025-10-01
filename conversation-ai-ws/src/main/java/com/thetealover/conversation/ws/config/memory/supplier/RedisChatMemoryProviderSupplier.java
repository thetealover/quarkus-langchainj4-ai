package com.thetealover.conversation.ws.config.memory.supplier;

import com.thetealover.conversation.ws.config.memory.RedisChatMemoryProvider;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import jakarta.enterprise.inject.spi.CDI;
import java.util.function.Supplier;

public class RedisChatMemoryProviderSupplier implements Supplier<ChatMemoryProvider> {
  @Override
  public ChatMemoryProvider get() {
    return CDI.current().select(RedisChatMemoryProvider.class).get();
  }
}
