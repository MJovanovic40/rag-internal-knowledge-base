package rs.raf.mjovanovic40.rag_internal_knowledge_base.auth.service;

import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.model.User;

public interface JwtService {

    String createToken(String id);
    User getUserFromToken(String token);
}
