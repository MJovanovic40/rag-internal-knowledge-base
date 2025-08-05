package rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.graph.state;

import org.bsc.langgraph4j.state.AgentState;
import org.bsc.langgraph4j.state.Channel;
import org.bsc.langgraph4j.state.Channels;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.model.ChatMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyAgentState extends AgentState {

    public static final String PROMPT_KEY = "prompt";
    public static final String ENHANCED_PROMPT_KEY = "enhancedPrompt";
    public static final String CONVERSATION_ID_KEY = "conversationId";
    public static final String MEMORY_KEY = "memory";
    public static final String RESPONSE_KEY = "response";
    public static final String USE_RAG_KEY = "useRag";
    public static final String USER_ID_KEY = "userId";
    public static final String CONTEXT_KEY = "context";
    public static final String MODEL_KEY = "model";

    public static final Map<String, Channel<?>> SCHEMA = Map.of(
            PROMPT_KEY, Channels.base((oldV, newV) -> newV),
            ENHANCED_PROMPT_KEY, Channels.base((oldV, newV) -> newV),
            CONVERSATION_ID_KEY, Channels.base((oldV, newV) -> newV),
            MEMORY_KEY, Channels.base((oldV, newV) -> newV),
            RESPONSE_KEY, Channels.base((oldV, newV) -> newV),
            USE_RAG_KEY, Channels.base((oldV, newV) -> newV),
            USER_ID_KEY, Channels.base((oldV, newV) -> newV),
            CONTEXT_KEY, Channels.base((oldV, newV) -> newV),
            MODEL_KEY, Channels.base((oldV, newV) -> newV)
    );

    public MyAgentState(Map<String, Object> initData) {
        super(initData);
    }

    public String prompt() {
        return this.<String>value(PROMPT_KEY).orElse("");
    }

    public String enhancedPrompt() {
        return this.<String>value(ENHANCED_PROMPT_KEY).orElse("");
    }

    public String conversationId() {
        return this.<String>value(CONVERSATION_ID_KEY).orElse("");
    }

    public List<ChatMessage> memory() {
        return this.<List<ChatMessage>>value(MEMORY_KEY).orElse(new ArrayList<>());
    }

    public String response() {
        return this.<String>value(RESPONSE_KEY).orElse("");
    }

    public Boolean useRag() {
        return this.<Boolean>value(USE_RAG_KEY).orElse(true);
    }

    public String userId() {
        return this.<String>value(USER_ID_KEY).orElse("");
    }

    public List<String> context() {
        return this.<List<String>>value(CONTEXT_KEY).orElse(new ArrayList<>());
    }

    public String model() {
        return this.<String>value(MODEL_KEY).orElse("");
    }
}
