package rs.raf.mjovanovic40.rag_internal_knowledge_base.auth.service;

import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.dto.UserDto;

public interface AuthService {

    String login(String email, String password);
    UserDto register(String email, String password);
}
