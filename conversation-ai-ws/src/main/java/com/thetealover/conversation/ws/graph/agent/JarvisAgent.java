package com.thetealover.conversation.ws.graph.agent;

import static java.lang.String.join;
import static java.util.Objects.isNull;

import com.thetealover.conversation.ws.graph.state.State;
import com.thetealover.conversation.ws.service.ai.common.Router;
import com.thetealover.conversation.ws.service.ai.quarkus.ClaudeBlockingAiJarvisService;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.NodeAction;

@Slf4j
@ApplicationScoped
public class JarvisAgent implements NodeAction<State> {
  private static final String[] MEMBER_NODES = {"weather_agent", "sports_agent", "FINISH"};

  @Inject ClaudeBlockingAiJarvisService service;

  @Override
  public Map<String, Object> apply(State state) {
    final ChatMessage chatMessage = state.lastMessage().orElseThrow();

    final String chatText =
        switch (chatMessage.type()) {
          case USER -> ((dev.langchain4j.data.message.UserMessage) chatMessage).singleText();
          case AI -> ((AiMessage) chatMessage).text();
          default ->
              throw new IllegalStateException("Unexpected message type: " + chatMessage.type());
        };

    final Router result = service.chat(join(",", MEMBER_NODES), chatText);

    log.info("Jarvis routing decision: {}", result);

    if (isNull(result) || isNull(result.next)) {
      log.warn("Jarvis could not determine the next step. Defaulting to FINISH.");
      return Map.of("next_node", "FINISH");
    }

    return Map.of("next_node", result.next);
  }
}
