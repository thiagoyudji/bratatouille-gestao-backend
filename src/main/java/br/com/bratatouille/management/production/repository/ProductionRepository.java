package br.com.bratatouille.management.production.repository;

import br.com.bratatouille.management.production.entity.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductionRepository extends JpaRepository<Production, Long> {

    @Query("""
        SELECT COALESCE(SUM(p.totalCost) / NULLIF(SUM(p.producedQuantity), 0), 0)
        FROM Production p
        WHERE p.recipe.outputItem.id = :itemId
    """)
    BigDecimal findAverageUnitCostByOutputItemId(Long itemId);
}