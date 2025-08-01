package rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.service;

import org.springframework.ai.chat.messages.Message;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.dto.ChatMessageDto;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.model.ChatMessage;

import java.util.List;

public interface ChatMessageService {

    List<ChatMessage> getChatHistory(String chatId);
    void addMessage(String chatId, Message message);
    void clearMessages(String chatId);
    List<ChatMessageDto> getChatHistoryDto(String chatId);
}
