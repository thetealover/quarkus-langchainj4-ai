package com.thetealover.conversation.ws.service.ai.quarkus;

import com.thetealover.conversation.ws.config.ai.supplier.claude.ClaudeBlockingLlmSupplier;
import com.thetealover.conversation.ws.service.ai.common.Router;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RegisterAiService(chatLanguageModelSupplier = ClaudeBlockingLlmSupplier.class)
public interface ClaudeBlockingAiJarvisService {
  @SystemMessage(
      """
            You are a supervisor AI agent tasked with managing
            the following worker agents: {{worker_agents}}.
            You must respond with the worker's name to act next when given the following user request.

            If the input does not fall under the topics of the worker agents, respond with FINISH.
      """)
  Router chat(@V("worker_agents") String workerAgents, @UserMessage String message);
}
