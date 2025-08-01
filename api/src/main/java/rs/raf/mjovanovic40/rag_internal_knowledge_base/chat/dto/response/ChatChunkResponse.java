package rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatChunkResponse {

    private String chatId;
    private String title;
    private String message;
}
