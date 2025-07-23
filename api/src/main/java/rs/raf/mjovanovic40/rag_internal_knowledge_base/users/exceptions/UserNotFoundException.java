package rs.raf.mjovanovic40.rag_internal_knowledge_base.users.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found.");
    }
}
