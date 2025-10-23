package com.thetealover.conversation.ws.graph;

import static org.bsc.langgraph4j.StateGraph.END;
import static org.bsc.langgraph4j.StateGraph.START;
import static org.bsc.langgraph4j.action.AsyncEdgeAction.edge_async;
import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

import com.thetealover.conversation.ws.graph.agent.JarvisAgent;
import com.thetealover.conversation.ws.graph.agent.SportsAgent;
import com.thetealover.conversation.ws.graph.agent.WeatherAgent;
import com.thetealover.conversation.ws.graph.state.State;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.CompiledGraph;
import org.bsc.langgraph4j.GraphStateException;
import org.bsc.langgraph4j.StateGraph;

@Slf4j
public class Graph {
  @Inject JarvisAgent jarvisAgent;
  @Inject SportsAgent sportsAgent;
  @Inject WeatherAgent weatherAgent;

  @Produces
  public CompiledGraph<State> conditionalSupervisedGraph() {

    CompiledGraph<State> compiledGraph;
    try {
      final StateGraph<State> stateStateGraph =
          new StateGraph<>(State.SCHEMA, State.serializer())
              // defining the nodes
              .addNode("jarvis", node_async(jarvisAgent))
              .addNode("sports_agent", node_async(sportsAgent))
              .addNode("weather_agent", node_async(weatherAgent))
              // starting node is Jarvis
              .addEdge(START, "jarvis")
              // defining the conditional edge
              .addConditionalEdges(
                  "jarvis", // conditional options will take place after Jarvis
                  // if a next() value is present, the next state is the value of next()
                  edge_async(
                      state ->
                          state
                              .next() // pulls out the next_node value from the state set by Jarvis
                              .orElseThrow(
                                  () -> new IllegalStateException("no next value is present"))),
                  // if next() == "weather_agent", go to weather_agent and so on
                  Map.of(
                      "weather_agent", "weather_agent",
                      "sports_agent", "sports_agent",
                      "FINISH", END))
              // defining the rest of the edges - pointing from subagents to Jarvis
              .addEdge("weather_agent", "jarvis")
              .addEdge("sports_agent", "jarvis");

      compiledGraph = stateStateGraph.compile();
    } catch (GraphStateException exception) {
      log.error("Error compiling graph", exception);
      return null;
    }
    return compiledGraph;
  }
}
