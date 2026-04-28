package br.com.bratatouille.management.operationalLoss.repository;

import br.com.bratatouille.management.operationalLoss.entity.OperationalLoss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationalLossRepository extends JpaRepository<OperationalLoss, Long> {
}