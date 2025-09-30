package com.thetealover.conversation.ws.service.ai.common.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;

public interface TokenStreamingService {
  TokenStream chat(@MemoryId final String memoryId, @UserMessage final String message);
}
