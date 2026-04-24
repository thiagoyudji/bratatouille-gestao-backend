package br.com.bratatouille.management.purchase.repository;

import br.com.bratatouille.management.purchase.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {

    @Query("""
        SELECT COALESCE(SUM(pi.totalValue) / NULLIF(SUM(pi.quantity), 0), 0)
        FROM PurchaseItem pi
        WHERE pi.item.id = :itemId
    """)
    BigDecimal findAverageUnitCostByItemId(Long itemId);
}