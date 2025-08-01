package rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DocumentDto {

    private String id;
    private String name;
    private Instant createdAt;
}
