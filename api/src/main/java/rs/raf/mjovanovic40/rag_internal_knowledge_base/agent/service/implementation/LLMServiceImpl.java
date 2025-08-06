package rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.provider.LLMProvider;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.service.LLMService;

@Service
@RequiredArgsConstructor
public class LLMServiceImpl implements LLMService {

    private final LLMProvider llmProvider;

    @Override
    public Flux<String> streamLLM(String model, Message ...messages) {
        return llmProvider.getChatClient(model)
                .prompt()
                .messages(messages)
                .stream()
                .content();
    }

    @Override
    public String promptLLM(String model, Message ...messages) {
        String resp = llmProvider.getChatClient(model)
                .prompt()
                .messages(messages)
                .call()
                .content();

        if (resp == null) return "[Error during generation]";

        int thinkEndIndex = resp.lastIndexOf("</think>");

        if(thinkEndIndex > 0) {
            resp = resp.substring(thinkEndIndex + 8).strip();
        }
        return resp;
    }
}
