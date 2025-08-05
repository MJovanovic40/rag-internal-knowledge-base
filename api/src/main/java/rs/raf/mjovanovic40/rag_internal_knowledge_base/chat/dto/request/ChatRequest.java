package rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatRequest {

    @NotNull
    @NotEmpty
    private String message;
    private String chatId;
    private Boolean useRag = true;
    private String model;
}