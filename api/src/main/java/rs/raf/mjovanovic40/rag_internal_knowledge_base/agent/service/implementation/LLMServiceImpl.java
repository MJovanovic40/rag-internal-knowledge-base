package rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.service.LLMService;

@Service
@RequiredArgsConstructor
public class LLMServiceImpl implements LLMService {

    private final ChatClient chatClient;

    @Override
    public Flux<String> streamLLM(Message ...messages) {
        return chatClient
                .prompt()
                .messages(messages)
                .stream()
                .content();
    }

    @Override
    public String promptLLM(Message ...messages) {
        return chatClient
                .prompt()
                .messages(messages)
                .call()
                .content();
    }
}
