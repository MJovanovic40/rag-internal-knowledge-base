package rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.model.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChat_Id(String id);

    @Query("DELETE FROM ChatMessage cm WHERE cm.chat.id = :chatId")
    @Modifying
    void deleteByChat(String chatId);
}
