package rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.graph;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.CompiledGraph;
import org.bsc.langgraph4j.StateGraph;
import org.springframework.stereotype.Component;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.graph.nodes.*;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.graph.state.MyAgentState;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.config.exception.CustomException;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.bsc.langgraph4j.StateGraph.END;
import static org.bsc.langgraph4j.StateGraph.START;
import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

@Slf4j
@Component
@RequiredArgsConstructor
public class LLMGraphProvider {

    public static final String FETCH_MEMORY_NODE = "fetch_memory";
    public static final String UPDATE_MEMORY_NODE = "update_memory";
    public static final String SIMILARITY_SEARCH_NODE = "similarity_search";
    public static final String PROMPT_ENHANCER_NODE = "prompt_enhancer";
    public static final String RESPONDER_NODE = "responder";

    private final FetchMemoryNode fetchMemoryNode;
    private final UpdateMemoryNode updateMemoryNode;
    private final SimilaritySearchNode similaritySearchNode;
    private final PromptEnhancerNode promptEnhancerNode;
    private final ResponderNode responderNode;

    public CompiledGraph<MyAgentState> getGraph() {
        try {
            return new StateGraph<>(MyAgentState.SCHEMA, MyAgentState::new)
                    .addNode(FETCH_MEMORY_NODE, node_async(fetchMemoryNode))
                    .addNode(UPDATE_MEMORY_NODE, node_async(updateMemoryNode))
                    .addNode(SIMILARITY_SEARCH_NODE, node_async(similaritySearchNode))
                    .addNode(PROMPT_ENHANCER_NODE, node_async(promptEnhancerNode))
                    .addNode(RESPONDER_NODE, node_async(responderNode))

                    .addEdge(START, FETCH_MEMORY_NODE)
                    .addEdge(FETCH_MEMORY_NODE, PROMPT_ENHANCER_NODE)
                    .addConditionalEdges(
                            PROMPT_ENHANCER_NODE,
                            state -> CompletableFuture.completedFuture(state.useRag() ? SIMILARITY_SEARCH_NODE : RESPONDER_NODE),
                            Map.of(SIMILARITY_SEARCH_NODE, SIMILARITY_SEARCH_NODE, RESPONDER_NODE, RESPONDER_NODE)
                    )
                    .addEdge(SIMILARITY_SEARCH_NODE, RESPONDER_NODE)
                    .addEdge(RESPONDER_NODE, UPDATE_MEMORY_NODE)
                    .addEdge(UPDATE_MEMORY_NODE, END)
                    .compile();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(e.getMessage());
        }
    }

}
