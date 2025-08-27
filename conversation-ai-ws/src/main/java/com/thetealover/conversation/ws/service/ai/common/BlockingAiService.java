package com.thetealover.conversation.ws.service.ai.common;

import dev.langchain4j.service.Result;
import java.util.List;

public interface BlockingAiService {
  String chat(String message);

  Result<String> chatForResponse(List<String> message);
}
