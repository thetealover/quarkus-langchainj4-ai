package com.thetealover.conversation.ws.service.ai.common.guardrail;

import com.thetealover.conversation.ws.service.ai.common.service.PromptInjectionDetectionService;
import dev.langchain4j.data.message.UserMessage;
import io.quarkiverse.langchain4j.guardrails.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PromptInjectionGuard implements InputGuardrail {
  private static final String PROMPT_INJECTION_DETECTED_ERROR_MSG = "Prompt injection detected";
  private static final double PROMPT_INJECTION_THRESHOLD = 0.7;

  @Inject PromptInjectionDetectionService promptInjectionDetectionService;

  @Override
  public InputGuardrailResult validate(final UserMessage userMessage) {
    final Double result = promptInjectionDetectionService.isInjection(userMessage.singleText());

    return result > PROMPT_INJECTION_THRESHOLD
        ? failure(PROMPT_INJECTION_DETECTED_ERROR_MSG)
        : success();
  }
}
