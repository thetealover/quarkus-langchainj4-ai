package com.thetealover.conversation.ws.service.common;

import dev.langchain4j.service.TokenStream;

public interface TokenStreamingAssistant {
  TokenStream chat(String message);
}
