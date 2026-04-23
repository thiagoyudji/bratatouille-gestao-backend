package br.com.bratatouille.management.purchase.repository;

import br.com.bratatouille.management.purchase.entity.PurchaseSplit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseSplitRepository extends JpaRepository<PurchaseSplit, Long> {
}