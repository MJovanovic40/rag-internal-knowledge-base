package rs.raf.mjovanovic40.rag_internal_knowledge_base.users;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.config.security.AppUserDetails;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.dto.UserDto;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<UserDto> getUser(@AuthenticationPrincipal AppUserDetails principal) {
        return ResponseEntity.ok(modelMapper.map(principal.getUser(), UserDto.class));
    }

}
