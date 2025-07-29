package rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatDto {

    private String id;
    private String title;
    private String userId;
    private Instant createdAt;
}
