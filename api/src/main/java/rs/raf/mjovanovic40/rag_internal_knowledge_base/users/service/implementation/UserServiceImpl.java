package rs.raf.mjovanovic40.rag_internal_knowledge_base.users.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.config.exception.CustomException;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.config.security.AppUserDetails;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.enums.Role;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.exceptions.UserNotFoundException;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.model.User;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.repository.UserRepository;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a new {@link User} with the given email and raw password.
     * <p>
     * The password is securely encoded using the configured {@link PasswordEncoder},
     * and the user's role is set to {@link Role#USER} by default.
     *
     * @param name the user's name
     * @param email the user's email address
     * @param password the user's raw password
     * @return the saved {@link User} entity
     *
     * @author Milan Jovanovic
     */
    @Override
    public User createUser(String name, String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        return save(user);
    }

    /**
     * Finds a user by their email address.
     *
     * @param email the email of the user to find
     * @return the {@link User} with the given email
     * @throws UserNotFoundException if no user is found with the specified email
     *
     * @author Milan Jovanovic
     */
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    /**
     * Finds a user by their unique identifier.
     *
     * @param id the ID of the user to find
     * @return the {@link User} with the given ID
     * @throws UserNotFoundException if no user is found with the specified ID
     *
     * @author Milan Jovanovic
     */
    @Override
    public User findUserById(String id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    /**
     * Checks if a user with the given email already exists in the system.
     *
     * @param email the email address to check for existence
     * @return {@code true} if a user with the given email exists, {@code false} otherwise
     *
     * @author Milan Jovanovic
     */
    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Loads user details by their email address for authentication purposes.
     *
     * @param email the email address used as the username
     * @return an {@link AppUserDetails} instance wrapping the found user
     * @throws UsernameNotFoundException if no user is found with the given email
     *
     * @author Milan Jovanovic
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new AppUserDetails(findUserByEmail(email));
    }

    /**
     * Attempts to save the given {@link User} entity to the repository.
     * <p>
     * Logs and rethrows a {@link CustomException} if the operation fails.
     *
     * @param user the user to save
     * @return the saved {@link User}
     * @throws CustomException if saving the user fails
     *
     * @author Milan Jovanovic
     */
    private User save(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Failed to save user", e);
            throw new CustomException("Failed to save user.");
        }
    }

}
