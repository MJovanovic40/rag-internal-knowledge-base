package rs.raf.mjovanovic40.rag_internal_knowledge_base.auth.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid credentials");
    }
}
