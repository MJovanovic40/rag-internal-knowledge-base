package rs.raf.mjovanovic40.rag_internal_knowledge_base.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.model.User;

public interface UserService extends UserDetailsService {

    User createUser(String email, String password);
    User findUserByEmail(String email);
    User findUserById(String id);
    Boolean existsByEmail(String email);
}
