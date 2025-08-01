package rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.exception;

public class DocumentNotFoundException extends RuntimeException {
  public DocumentNotFoundException() {
    super("Requested document was not found.");
  }
}
