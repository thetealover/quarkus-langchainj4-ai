package com.thetealover.conversation.ws.service.ai.common.service;

import static com.thetealover.conversation.ws.persistence.mysql.model.ChatMessageSource.*;

import com.thetealover.conversation.ws.persistence.mysql.service.ChatHistoryService;
import com.thetealover.conversation.ws.service.ai.common.event.AiResponseCompletedEvent;
import com.thetealover.conversation.ws.service.ai.common.event.AiResponseErrorEvent;
import com.thetealover.conversation.ws.service.ai.common.event.ToolExecutedEvent;
import com.thetealover.conversation.ws.service.ai.common.event.UserMessageSentEvent;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class ChatHistoryObserver {

  @Inject ChatHistoryService chatHistoryService;

  public void observeUserMessage(@Observes final UserMessageSentEvent event) {
    log.debug("Observed UserMessageSentEvent for chat id: {}", event.getChatId());
    chatHistoryService.saveMessage(event.getChatId(), USER, event.getMessage());
  }

  public void observeAiResponse(@ObservesAsync final AiResponseCompletedEvent event) {
    log.debug("Observed AiResponseCompletedEvent for chat id: {}", event.getChatId());
    chatHistoryService.saveMessage(event.getChatId(), AI, event.getResponse());
  }

  public void observeToolExecution(@ObservesAsync final ToolExecutedEvent event) {
    log.debug("Observed ToolExecutedEvent for chat id: {}", event.getChatId());
    chatHistoryService.saveMessage(event.getChatId(), TOOL, event.getMessage());
  }

  public void observeAiError(@ObservesAsync final AiResponseErrorEvent event) {
    log.debug("Observed AiResponseErrorEvent for chat id: {}", event.getChatId());
    String errorMessage = "[ERROR] " + event.getErrorMessage();
    chatHistoryService.saveMessage(event.getChatId(), AI, errorMessage);
  }
}
