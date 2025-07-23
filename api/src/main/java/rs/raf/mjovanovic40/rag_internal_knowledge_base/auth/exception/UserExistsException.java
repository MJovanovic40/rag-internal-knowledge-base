package rs.raf.mjovanovic40.rag_internal_knowledge_base.auth.exception;

public class UserExistsException extends RuntimeException {
    public UserExistsException() {
        super("User with the same name already exists.");
    }
}
