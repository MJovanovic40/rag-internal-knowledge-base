package rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.graph.nodes;

import lombok.RequiredArgsConstructor;
import org.bsc.langgraph4j.action.NodeAction;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Component;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.graph.state.MyAgentState;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.service.LLMService;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.model.ChatMessage;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.config.exception.CustomException;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PromptEnhancerNode implements NodeAction<MyAgentState> {

    private final LLMService llmService;

    @Override
    public Map<String, Object> apply(MyAgentState myAgentState){
        String systemPromptTemplate = """
    You are a query rewriter for a search engine. Your goal is to help clarify ambiguous questions only when truly necessary.

    Strict instructions:
    - If the conversation history is empty, return the original question exactly.
    - Only rewrite the question if it is unclear or incomplete *on its own* and the context clearly helps clarify it.
    - Never add information, assumptions, or detail that is not already present.
    - If the original question is already complete and understandable, return it unchanged.
    - Do not paraphrase, reword, or reformat unless clarity strictly requires it.
    - Output only the final query. No explanations or extra text.

    Conversation history:
    [{memory}]
    
    User's query:
    {query}
    
    Rewritten query:
    """;

        Message systemMessage = PromptTemplate
                .builder()
                .template(systemPromptTemplate)
                .build()
                .createMessage(Map.of(
                        "memory", myAgentState.memory().stream().map(this::getMessage).toList(),
                        "query", myAgentState.prompt()
                ));

        String newPrompt = llmService.promptLLM(myAgentState.model(), systemMessage);

        if(newPrompt == null) throw new CustomException("PromptEnhancerNode: Cannot generate enhanced prompt.");

        System.out.println(newPrompt);

        return Map.of(MyAgentState.ENHANCED_PROMPT_KEY, newPrompt);
    }

    private Message getMessage(ChatMessage chatMessage) {
        if(MessageType.USER.equals(chatMessage.getType())) return new UserMessage(chatMessage.getMessage());
        return new AssistantMessage(chatMessage.getMessage());
    }
}
