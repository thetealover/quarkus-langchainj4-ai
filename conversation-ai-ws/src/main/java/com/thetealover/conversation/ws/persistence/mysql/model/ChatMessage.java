package com.thetealover.conversation.ws.persistence.mysql.model;

import static jakarta.persistence.EnumType.STRING;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_message")
public class ChatMessage extends PanacheEntity {

  @Column(name = "chat_id", nullable = false)
  private String chatId;

  @Enumerated(STRING)
  @Column(nullable = false)
  private ChatMessageSource source;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String message;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;
}
