package rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LLMProvider {

    private ChatModel getChatModel(String model) {
        return OllamaChatModel
                .builder()
                .ollamaApi(OllamaApi.builder().build())
                .defaultOptions(
                        OllamaOptions.builder()
                                .model(model)
                                .temperature(0d)
                        .build()
                )
                .build();
    }

    public ChatClient getChatClient(String model) {
        return ChatClient
                .builder(getChatModel(model))
                .build();
    }
}
