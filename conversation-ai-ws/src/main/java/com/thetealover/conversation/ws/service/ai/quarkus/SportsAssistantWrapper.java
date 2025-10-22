package com.thetealover.conversation.ws.service.ai.quarkus;

import com.thetealover.conversation.ws.config.ai.qualifier.service.SportsAgentAssistant;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SportsAssistantWrapper {
  @Inject @SportsAgentAssistant SportsAssistant sportsAssistant;

  @Tool("A sports expert")
  public String chat(final String message) {
    return sportsAssistant.chatWIthSportsAssistant(message);
  }
}
