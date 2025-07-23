package rs.raf.mjovanovic40.rag_internal_knowledge_base.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    /**
     * Creates and provides a {@link PasswordEncoder} bean using {@link BCryptPasswordEncoder}.
     * <p>
     * This bean is used for encoding and verifying user passwords securely
     * using the BCrypt hashing algorithm.
     *
     * @return a {@link BCryptPasswordEncoder} instance
     *
     * @author Milan Jovanovic
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
