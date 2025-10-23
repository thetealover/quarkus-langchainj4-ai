package com.thetealover.conversation.ws.graph.state;

import dev.langchain4j.data.message.ChatMessage;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bsc.langgraph4j.langchain4j.serializer.std.LC4jStateSerializer;
import org.bsc.langgraph4j.prebuilt.MessagesState;
import org.bsc.langgraph4j.serializer.StateSerializer;

public class State extends MessagesState<ChatMessage> {
  public Optional<String> next() {
    return this.value("next_node"); // points to the next node, Jarvis is going to set the value
  }

  public State(Map<String, Object> initData) {
    super(initData);
  }

  public static StateSerializer<State> serializer() {
    return new LC4jStateSerializer<>(State::new);
  }

  @Override
  public List<ChatMessage> messages() {
    return super.messages();
  }
}
