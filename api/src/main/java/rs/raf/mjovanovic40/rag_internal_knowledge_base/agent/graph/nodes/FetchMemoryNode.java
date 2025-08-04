package rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.graph.nodes;

import lombok.RequiredArgsConstructor;
import org.bsc.langgraph4j.action.NodeAction;
import org.springframework.stereotype.Component;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.graph.state.MyAgentState;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.model.ChatMessage;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.service.ChatMessageService;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FetchMemoryNode implements NodeAction<MyAgentState> {

    private static final Integer MAX_MESSAGE_WINDOW = 5;

    private final ChatMessageService chatMessageService;

    @Override
    public Map<String, Object> apply(MyAgentState myAgentState) {
        List<ChatMessage> messages = chatMessageService.getChatHistory(myAgentState.conversationId());

        List<ChatMessage> memory = messages.stream().limit(MAX_MESSAGE_WINDOW).toList();

        return Map.of(MyAgentState.MEMORY_KEY, memory);
    }
}
