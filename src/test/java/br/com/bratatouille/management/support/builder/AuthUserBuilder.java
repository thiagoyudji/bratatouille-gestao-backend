package br.com.bratatouille.management.support.builder;

import br.com.bratatouille.management.auth.entity.AuthUser;
import br.com.bratatouille.management.auth.entity.UserRole;
import org.springframework.test.util.ReflectionTestUtils;

public class AuthUserBuilder {

    private String username = "user";
    private String passwordHash = "hashed-password";
    private UserRole role = UserRole.CUSTOMER;
    private boolean active = true;

    public AuthUserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public AuthUserBuilder withPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public AuthUserBuilder withRole(UserRole role) {
        this.role = role;
        return this;
    }

    public AuthUserBuilder inactive() {
        this.active = false;
        return this;
    }

    public AuthUserBuilder withActive(boolean active) {
        this.active = active;
        return this;
    }

    public AuthUser build() {
        AuthUser user = new AuthUser(username, passwordHash, role);
        ReflectionTestUtils.setField(user, "active", active);
        return user;
    }
}
