package rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.model.Document;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.service.MinioService;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.service.VectorStoreService;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.utils.FileUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VectorStoreServiceImpl implements VectorStoreService {

    private static final String ID_FIELD = "id";

    private final VectorStore vectorStore;
    private final MinioService minioService;

    @Override
    @Async
    public void addToVectorStore(Document document) {
        String objectName = FileUtils.getObjectName(document.getName(), document.getId());

        Resource resource = new InputStreamResource(minioService.getObject(objectName));
        PagePdfDocumentReader reader = new PagePdfDocumentReader(resource);

        List<org.springframework.ai.document.Document> documents = reader.read()
                .stream()
                .map(doc -> {
                    if(doc.getText() == null) return null;
                    return new org.springframework.ai.document.Document(doc.getText(), Map.of(ID_FIELD, document.getId()));
                })
                .filter(Objects::nonNull)
                .toList();

        vectorStore.add(documents);
    }

    @Override
    @Async
    public void removeFromVectorStore(String documentId) {
        Filter.Expression expression = new FilterExpressionBuilder().eq(ID_FIELD, documentId).build();
        vectorStore.delete(expression);
    }
}
