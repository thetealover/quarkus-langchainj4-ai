package com.thetealover.conversation.ws.service.ai.common;

import dev.langchain4j.model.output.structured.Description;

public class Router {
  @Description("Worker to route to next. If no workers needed, route to FINISH.")
  public String next;

  @Override
  public String toString() {
    return "Router[next: %s]".formatted(next);
  }
}
