package com.thetealover.conversation.ws.service.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@RegisterAiService
@SystemMessage(
    """
        You're a general-purpose AI assistant. Your job is to provide answers to user questions.
        If your answer is not factual, add a disclaimer [not factual] at the beginning of your answer.
        Don't provide subjective opinions, assumptions, and speculations.
        Keep your answers concise and relevant, but not more than necessary.
        If you don't know the answer, say that you don't know it.
    """)
@ApplicationScoped
public interface OllamaGeneralService {

  @UserMessage("I got a request, {message}.")
  String askQuestion(String message);
}
