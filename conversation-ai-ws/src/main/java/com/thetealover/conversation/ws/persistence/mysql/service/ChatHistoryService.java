package com.thetealover.conversation.ws.persistence.mysql.service;

import com.thetealover.conversation.ws.persistence.mysql.model.ChatMessage;
import com.thetealover.conversation.ws.persistence.mysql.model.ChatMessageSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class ChatHistoryService {

  @Transactional
  public void saveMessage(
      final String chatId, final ChatMessageSource source, final String message) {
    final ChatMessage chatMessage =
        ChatMessage.builder().chatId(chatId).source(source).message(message).build();

    chatMessage.persist();
  }
}
