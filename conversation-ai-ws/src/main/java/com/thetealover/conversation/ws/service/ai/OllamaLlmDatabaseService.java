package com.thetealover.conversation.ws.service.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@RegisterAiService(toolProviderSupplier = RegisterAiService.NoToolProviderSupplier.class)
@SystemMessage(
    """
          You're an expert in databases. Keep your answers concise and to the point.
          Use no more than 300 characters in your answers.
          If you think that the answer will be wrong by more than 40%, ask 3 additional questions to the user.
          If the answers are not sufficient, ask for more information - each time by 3 questions.
          Cover the database choice from the following aspects: scaling, ease of use, performance.
    """)
@ApplicationScoped
public interface OllamaLlmDatabaseService {

  @UserMessage("Advise me on a database selection. {message}")
  String askAdviseOnDatabaseSelection(String message);
}
