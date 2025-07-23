package rs.raf.mjovanovic40.rag_internal_knowledge_base.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
}
