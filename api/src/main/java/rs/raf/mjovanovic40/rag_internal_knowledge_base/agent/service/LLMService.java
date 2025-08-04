package rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.service;

import org.springframework.ai.chat.messages.Message;
import reactor.core.publisher.Flux;

public interface LLMService {

    Flux<String> streamLLM(Message ...messages);
    String promptLLM(Message ...messages);
}
