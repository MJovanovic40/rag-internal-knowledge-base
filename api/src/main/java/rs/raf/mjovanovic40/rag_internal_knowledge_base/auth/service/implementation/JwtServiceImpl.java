package rs.raf.mjovanovic40.rag_internal_knowledge_base.auth.service.implementation;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.auth.service.JwtService;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.config.properties.AppProperties;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.model.User;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.service.UserService;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final UserService userService;
    private final AppProperties appProperties;

    /**
     * Creates a signed JWT token for the given user ID.
     * <p>
     * The token includes the user ID as the subject, the current timestamp as the issue time,
     * and an expiration date set 24 years from the time of issuance.
     *
     * @param id the user's unique identifier to include as the token subject
     * @return a signed JWT token string
     *
     * @author Milan Jovanovic
     */
    @Override
    public String createToken(String id) {
        return Jwts
                .builder()
                .subject(id)
                .signWith(getKey())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(24, ChronoUnit.YEARS)))
                .compact();
    }

    /**
     * Extracts the user ID from the given JWT token and retrieves the corresponding {@link User}.
     *
     * @param token the JWT token containing the user ID
     * @return the {@link User} associated with the token's subject
     * @throws io.jsonwebtoken.JwtException if the token is invalid or cannot be parsed
     *
     * @author Milan Jovanovic
     */
    @Override
    public User getUserFromToken(String token) {
        return userService.findUserById(
                Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().getSubject()
        );
    }

    /**
     * Decodes the Base64-encoded secret and returns a {@link SecretKey} for signing or verifying JWT tokens.
     *
     * @return the decoded and prepared {@link SecretKey}
     *
     * @author Milan Jovanovic
     */
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(appProperties.getJwtSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
