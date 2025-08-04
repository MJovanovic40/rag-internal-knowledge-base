package rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.graph.nodes;

import lombok.RequiredArgsConstructor;
import org.bsc.langgraph4j.action.NodeAction;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Component;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.graph.state.MyAgentState;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.service.ChatMessageService;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UpdateMemoryNode implements NodeAction<MyAgentState> {

    private final ChatMessageService chatMessageService;

    @Override
    public Map<String, Object> apply(MyAgentState myAgentState) {
        chatMessageService.addMessage(myAgentState.conversationId(), new UserMessage(myAgentState.prompt()));
        chatMessageService.addMessage(myAgentState.conversationId(), new AssistantMessage(myAgentState.response()));

        return Map.of();
    }
}
