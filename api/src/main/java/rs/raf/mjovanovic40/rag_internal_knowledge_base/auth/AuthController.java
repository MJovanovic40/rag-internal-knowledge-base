package rs.raf.mjovanovic40.rag_internal_knowledge_base.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.auth.dto.request.LoginRequest;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.auth.dto.request.RegisterRequest;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.auth.dto.response.LoginResponse;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.auth.service.AuthService;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.dto.UserDto;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Validated RegisterRequest body) {
        return ResponseEntity.ok(authService.register(body.getEmail(), body.getPassword()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest body) {
        return ResponseEntity.ok(
                new LoginResponse(
                        authService.login(body.getEmail(), body.getPassword())
                )
        );
    }

}
