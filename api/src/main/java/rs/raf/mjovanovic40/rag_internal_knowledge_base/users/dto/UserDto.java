package rs.raf.mjovanovic40.rag_internal_knowledge_base.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.enums.Role;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private String id;
    private String name;
    private String email;
    private Role role;
    private Instant createdAt;
}
