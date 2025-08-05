package rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.service;

import org.springframework.ai.chat.messages.Message;
import reactor.core.publisher.Flux;

public interface LLMService {

    Flux<String> streamLLM(String model, Message ...messages);
    String promptLLM(String model, Message ...messages);
}
