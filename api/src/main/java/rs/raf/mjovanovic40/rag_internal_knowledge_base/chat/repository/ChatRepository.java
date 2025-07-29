package rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.model.Chat;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, String> {
    List<Chat> findByUser_Id(String id);
}
