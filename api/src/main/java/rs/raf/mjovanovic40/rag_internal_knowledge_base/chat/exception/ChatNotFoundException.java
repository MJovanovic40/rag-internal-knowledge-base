package rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.exception;

public class ChatNotFoundException extends RuntimeException {
  public ChatNotFoundException(String message) {
    super(message);
  }
}
