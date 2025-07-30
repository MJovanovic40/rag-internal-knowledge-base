package rs.raf.mjovanovic40.rag_internal_knowledge_base.auth.service.implementation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.auth.exception.InvalidCredentialsException;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.auth.exception.UserExistsException;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.auth.service.AuthService;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.auth.service.JwtService;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.dto.UserDto;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.model.User;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(String email, String password) {
        final User user;
        try {
            user = userService.findUserByEmail(email);
        } catch (UserExistsException e) {
            throw new InvalidCredentialsException();
        }

        if(!passwordEncoder.matches(password, user.getPassword())) throw new InvalidCredentialsException();

        return jwtService.createToken(user.getId());
    }

    @Override
    public UserDto register(String name, String email, String password) {
        if(Boolean.TRUE.equals(userService.existsByEmail(email))) {
            throw new UserExistsException();
        }
        return modelMapper.map(userService.createUser(name, email, password), UserDto.class);
    }
}
