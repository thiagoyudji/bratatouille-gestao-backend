package br.com.bratatouille.management.financial.entity;

import br.com.bratatouille.management.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}