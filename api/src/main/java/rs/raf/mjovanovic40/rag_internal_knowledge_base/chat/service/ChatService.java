package rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.service;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.dto.ChatDto;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.dto.response.ChatChunkResponse;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.model.Chat;

import java.util.List;

public interface ChatService {

    Chat findById(String id);
    Chat create(String title, String userId);
    void delete(String id);
    Flux<ServerSentEvent<ChatChunkResponse>> sendMessage(String chatId, String message, String userId);
    List<ChatDto> getUserChats(String userId);
}