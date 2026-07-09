package br.com.bratatouille.management.purchase.repository;

import br.com.bratatouille.management.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByPurchaseDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("""
    SELECT COALESCE(SUM(p.totalAmount), 0)
    FROM Purchase p
    WHERE p.purchaseDate < :date
""")
    BigDecimal sumTotalAmountBefore(LocalDate date);
}