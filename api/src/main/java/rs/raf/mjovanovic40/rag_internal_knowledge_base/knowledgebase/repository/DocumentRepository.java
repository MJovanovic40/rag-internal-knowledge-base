package rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.model.Document;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, String> {

    List<Document> findByUser_Id(String id);
}
