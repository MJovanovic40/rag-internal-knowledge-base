package rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.MessageType;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {

    private Long id;
    private String message;
    private MessageType messageType;
    private Instant createdAt;
}
