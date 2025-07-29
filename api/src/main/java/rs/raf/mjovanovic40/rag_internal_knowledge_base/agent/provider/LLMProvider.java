package rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LLMProvider {

    private ChatModel getChatModel() {
        return OllamaChatModel
                .builder()
                .ollamaApi(OllamaApi.builder().build())
                .defaultOptions(
                        OllamaOptions.builder()
                                .model("llama3.1")
                                .temperature(0d)
                        .build()
                )
                .build();
    }

    @Bean
    private ChatClient getChatClient() {
        return ChatClient
                .builder(getChatModel())
                .build();
    }
}
