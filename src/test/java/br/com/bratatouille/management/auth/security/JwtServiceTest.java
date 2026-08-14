package br.com.bratatouille.management.auth.security;

import br.com.bratatouille.management.auth.entity.AuthUser;
import br.com.bratatouille.management.auth.entity.UserRole;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtServiceTest {

    @Test
    void generatesAndReadsTokenWithRuntimeJjwtDependencies() {
        JwtService service = new JwtService(
                "01234567890123456789012345678901",
                480
        );
        AuthUser user = new AuthUser("admin", "hashed-password", UserRole.ADMIN);

        String token = service.generateToken(user);

        assertEquals("admin", service.extractUsername(token));
        assertEquals(UserRole.ADMIN, service.extractRole(token));
        assertTrue(service.extractExpiresAt(token).isAfter(Instant.now()));
        assertTrue(service.isTokenValid(token, "admin"));
    }
}
