package rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.memory;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.*;
import org.springframework.stereotype.Component;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.model.ChatMessage;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.service.ChatMessageService;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MyChatMemory implements ChatMemory {

    private final ChatMessageService chatMessageService;
    private static final Integer MESSAGES_MEMORY_WINDOW = 5;

    @Override
    public void add(String conversationId, Message message) {
        chatMessageService.addMessage(conversationId, message);
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        messages.forEach(message -> add(conversationId, message));
    }

    @Override
    public List<Message> get(String conversationId) {
        List<ChatMessage> messages = chatMessageService.getChatHistory(conversationId);

        Collections.reverse(messages); // Get the latest messages

        return messages
                .stream()
                .limit(MESSAGES_MEMORY_WINDOW)
                .map(chatMessage -> {
                    final Message message;
                    switch (chatMessage.getType()) {
                        case MessageType.USER -> message = new UserMessage(chatMessage.getMessage());
                        case MessageType.ASSISTANT -> message = new AssistantMessage(chatMessage.getMessage());
                        case MessageType.SYSTEM -> message = new SystemMessage(chatMessage.getMessage());
                        default -> message = null;
                    }
                    return message;
                })
                .toList();
    }

    @Override
    public void clear(String conversationId) {
        chatMessageService.clearMessages(conversationId);
    }
}
