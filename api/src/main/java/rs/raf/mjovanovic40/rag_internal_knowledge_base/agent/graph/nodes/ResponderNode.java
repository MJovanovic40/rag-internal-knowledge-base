package rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.graph.nodes;

import lombok.RequiredArgsConstructor;
import org.bsc.langgraph4j.action.NodeAction;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Component;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.graph.state.MyAgentState;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.service.LLMService;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResponderNode implements NodeAction<MyAgentState> {

    private final LLMService llmService;

    @Override
    public Map<String, Object> apply(MyAgentState state) throws Exception {
        String resp;

        if(Boolean.FALSE.equals(state.useRag())) {
             resp = llmService.promptLLM(new UserMessage(state.enhancedPrompt()));
             return Map.of(MyAgentState.RESPONSE_KEY, resp);
        }

        String systemPrompt = """
                You are an intelligent assistant designed to answer user questions based on provided context.
                
                Always follow this behavior:
                - If the provided context is **empty or missing**, reply with:
                  "I'm sorry, but I couldn't find any relevant information to answer your question."
                - If the context **is present**, use it exclusively to answer the question.
                - Do not fabricate information or go beyond what is in the context.
                - Do not mention that you are using context or retrieval, just respond as naturally and helpfully as possible based on the information given.
                
                Context:
                [{context}]
                
                User question:
                """;


        if(state.context().isEmpty()) {
            resp = "I'm sorry, but I couldn't find any relevant information to answer your question.";
            return Map.of(MyAgentState.RESPONSE_KEY, resp);
        }

        Message systemMessage = new SystemPromptTemplate(systemPrompt).createMessage(Map.of("context", state.context()));

        resp = llmService.promptLLM(systemMessage, new UserMessage(state.enhancedPrompt()));

        return Map.of(MyAgentState.RESPONSE_KEY, resp);
    }
}
