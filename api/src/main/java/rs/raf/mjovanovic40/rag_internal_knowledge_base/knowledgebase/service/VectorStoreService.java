package rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.service;

import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.model.Document;

public interface VectorStoreService {

    String ID_FIELD = "id";
    String USER_ID_FIELD = "user_id";

    void addToVectorStore(Document document);
    void removeFromVectorStore(String documentId);
}
