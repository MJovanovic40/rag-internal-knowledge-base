package rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.service.LLMService;

@Service
@RequiredArgsConstructor
public class LLMServiceImpl implements LLMService {

    private final ChatClient chatClient;

    @Autowired
    @Lazy
    private ChatMemory chatMemory;

    @Override
    public Flux<String> streamLLM(String chatId, Message message) {
        return chatClient
                .prompt()
                .advisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .advisors(s -> s.param(ChatMemory.CONVERSATION_ID, chatId))
                .messages(message)
                .stream()
                .content();
    }

    @Override
    public String promptLLM(Message message) {
        return chatClient
                .prompt()
                .messages(message)
                .call()
                .content();
    }
}
