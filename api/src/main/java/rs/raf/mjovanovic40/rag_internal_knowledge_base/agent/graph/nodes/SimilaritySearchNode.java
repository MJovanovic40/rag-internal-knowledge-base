package rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.graph.nodes;

import lombok.RequiredArgsConstructor;
import org.bsc.langgraph4j.action.NodeAction;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Component;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.graph.state.MyAgentState;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.config.exception.CustomException;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.service.VectorStoreService;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SimilaritySearchNode implements NodeAction<MyAgentState> {

    private final VectorStore vectorStore;

    @Override
    public Map<String, Object> apply(MyAgentState myAgentState) {

        SearchRequest searchRequest = SearchRequest
                .builder()
                .filterExpression(
                        new FilterExpressionBuilder()
                                .eq(VectorStoreService.USER_ID_FIELD, myAgentState.userId())
                                .build()
                )
                .query(myAgentState.enhancedPrompt())
                .build();


        List<Document> documents = vectorStore.similaritySearch(searchRequest);

        if(documents == null) throw new CustomException("SimilaritySearchNode: Error while getting context.");

        List<String> context = documents.stream().map(Document::getText).toList();

        return Map.of(MyAgentState.CONTEXT_KEY, context);
    }
}
