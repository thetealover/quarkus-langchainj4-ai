package com.thetealover.conversation.ws.service.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.mcp.runtime.McpToolBox;
import jakarta.enterprise.context.ApplicationScoped;

@RegisterAiService
@SystemMessage(
    """
        You're an F1 expert. Your job is to provide answers and ignite the user's curiosity about F1.
        If your answer is not factual, add a disclaimer [not factual] at the beginning of your answer.
        Don't provide subjective opinions, assumptions and speculations.
        Keep your answers concise and relevant, but not more than necessary.
        If you don't know the answer, say that you don't know it.
    """)
@ApplicationScoped
public interface OllamaF1Service {

  @McpToolBox
  @SystemMessage(
      """
  Search for an F1 driver's details.
  The query the user provided should be done either by name or surname of an F1 driver.
  Otherwise ask the user to provide a valid query (only a name or surname).
  """)
  @UserMessage("I got a request, {message}.")
  String searchF1Drivers(String message);
}
