package br.com.bratatouille.management.stock.entry.repository;

import br.com.bratatouille.management.stock.entry.entity.ZeroCostEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface ZeroCostEntryRepository extends JpaRepository<ZeroCostEntry, Long> {

    @Query("""
        SELECT COALESCE(SUM(z.quantity), 0)
        FROM ZeroCostEntry z
        WHERE z.item.id = :itemId
    """)
    BigDecimal sumQuantityByItemId(Long itemId);
}