package rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.model.ChatMessage;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.repository.ChatMessageRepository;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.service.ChatMessageService;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.service.ChatService;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.config.exception.CustomException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatService chatService;

    @Override
    public List<ChatMessage> getChatHistory(String chatId) {
        return chatMessageRepository.findByChat_Id(chatId);
    }

    @Override
    public void addMessage(String chatId, Message message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(message.getMessageType());
        chatMessage.setChat(chatService.findById(chatId));
        chatMessage.setMessage(message.getText());
        save(chatMessage);
    }

    @Override
    public void clearMessages(String chatId) {
        chatMessageRepository.deleteByChat(chatId);
    }

    private ChatMessage save(ChatMessage chatMessage) {
        try {
            return chatMessageRepository.save(chatMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(e.getMessage());
        }
    }
}
