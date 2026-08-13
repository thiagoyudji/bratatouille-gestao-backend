package br.com.bratatouille.management.customer.repository;

import br.com.bratatouille.management.customer.entity.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {
    Optional<CustomerProfile> findByAuthUserUsername(String username);
}
