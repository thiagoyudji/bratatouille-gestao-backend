// TODO move this class to src/main/java/br/com/bratatouille/management/auth/security/JwtService.java when directory creation is available.
package br.com.bratatouille.management.auth.security;

import br.com.bratatouille.management.auth.entity.AuthUser;
import br.com.bratatouille.management.auth.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey key;
    private final long expirationMinutes;

    public JwtService(
            @Value("${auth.jwt.secret}") String secret,
            @Value("${auth.jwt.expiration-minutes:480}") long expirationMinutes
    ) {
        this.key = buildKey(secret);
        this.expirationMinutes = expirationMinutes;
    }

    public String generateToken(AuthUser user) {
        Instant now = Instant.now();
        Instant expiresAt = now.plus(expirationMinutes, ChronoUnit.MINUTES);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole().name())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiresAt))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getBody().getSubject();
    }

    public UserRole extractRole(String token) {
        String role = getClaims(token).getBody().get("role", String.class);
        return UserRole.valueOf(role);
    }

    public Instant extractExpiresAt(String token) {
        return getClaims(token).getBody().getExpiration().toInstant();
    }

    public boolean isTokenValid(String token, String expectedUsername) {
        return expectedUsername.equals(extractUsername(token)) && extractExpiresAt(token).isAfter(Instant.now());
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    private SecretKey buildKey(String secret) {
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        if (bytes.length < 32) {
            throw new IllegalStateException("auth.jwt.secret must be at least 32 bytes long");
        }

        return Keys.hmacShaKeyFor(bytes);
    }
}
