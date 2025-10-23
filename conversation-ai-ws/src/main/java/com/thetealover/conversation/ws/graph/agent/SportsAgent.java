package com.thetealover.conversation.ws.graph.agent;

import com.thetealover.conversation.ws.graph.state.State;
import com.thetealover.conversation.ws.service.ai.quarkus.ClaudeBlockingAiSportsService;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.NodeAction;

@Slf4j
@ApplicationScoped
public class SportsAgent implements NodeAction<State> {
  @Inject ClaudeBlockingAiSportsService service;

  @Override
  public Map<String, Object> apply(State state) {
    final ChatMessage lastMessage = state.lastMessage().orElseThrow();

    final String messageText =
        switch (lastMessage.type()) {
          case USER -> ((UserMessage) lastMessage).singleText();
          case AI -> ((AiMessage) lastMessage).text();
          default ->
              throw new IllegalStateException("Unexpected message type: " + lastMessage.type());
        };

    final String result = service.chat(messageText);

    log.info("SportsAgent result: {}", result);
    return Map.of("messages", AiMessage.from(result));
  }
}
