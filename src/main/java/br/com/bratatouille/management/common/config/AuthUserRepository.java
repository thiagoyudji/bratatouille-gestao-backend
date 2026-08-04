// TODO move this class to src/main/java/br/com/bratatouille/management/auth/repository/AuthUserRepository.java when directory creation is available.
package br.com.bratatouille.management.auth.repository;

import br.com.bratatouille.management.auth.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByUsername(String username);

    boolean existsByUsername(String username);
}
