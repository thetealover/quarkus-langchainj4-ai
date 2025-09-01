package com.thetealover.conversation.ws.service.ai.weather.guardrail;

import dev.langchain4j.data.message.AiMessage;
import io.quarkiverse.langchain4j.guardrails.*;

// todo enhance this and include in a service.
public class WeatherInputGuardrail implements OutputGuardrail {
  @Override
  public OutputGuardrailResult validate(AiMessage responseFromLLM) {
    return OutputGuardrail.super.validate(responseFromLLM);
  }

  @Override
  public OutputGuardrailResult validate(OutputGuardrailParams params) {
    return OutputGuardrail.super.validate(params);
  }

  @Override
  public OutputGuardrailResult success() {
    return OutputGuardrail.super.success();
  }

  @Override
  public OutputGuardrailResult successWith(String successfulText) {
    return OutputGuardrail.super.successWith(successfulText);
  }

  @Override
  public OutputGuardrailResult successWith(String successfulText, Object successfulResult) {
    return OutputGuardrail.super.successWith(successfulText, successfulResult);
  }

  @Override
  public OutputGuardrailResult failure(String message) {
    return OutputGuardrail.super.failure(message);
  }

  @Override
  public OutputGuardrailResult failure(String message, Throwable cause) {
    return OutputGuardrail.super.failure(message, cause);
  }

  @Override
  public OutputGuardrailResult fatal(String message) {
    return OutputGuardrail.super.fatal(message);
  }

  @Override
  public OutputGuardrailResult fatal(String message, Throwable cause) {
    return OutputGuardrail.super.fatal(message, cause);
  }

  @Override
  public OutputGuardrailResult retry(String message) {
    return OutputGuardrail.super.retry(message);
  }

  @Override
  public OutputGuardrailResult retry(String message, Throwable cause) {
    return OutputGuardrail.super.retry(message, cause);
  }

  @Override
  public OutputGuardrailResult reprompt(String message, String reprompt) {
    return OutputGuardrail.super.reprompt(message, reprompt);
  }

  @Override
  public OutputGuardrailResult reprompt(String message, Throwable cause, String reprompt) {
    return OutputGuardrail.super.reprompt(message, cause, reprompt);
  }
}
