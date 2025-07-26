package rs.raf.mjovanovic40.rag_internal_knowledge_base.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.config.exception.CustomErrorResponse;

@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CustomErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return new ResponseEntity<>(new CustomErrorResponse(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<CustomErrorResponse> handleUserExistsException(UserExistsException ex) {
        return new ResponseEntity<>(new CustomErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
