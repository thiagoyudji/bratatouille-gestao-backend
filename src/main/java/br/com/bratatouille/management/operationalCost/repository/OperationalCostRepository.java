package br.com.bratatouille.management.operationalCost.repository;

import br.com.bratatouille.management.operationalCost.entity.OperationalCost;
import br.com.bratatouille.management.operationalCost.entity.OperationalCostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OperationalCostRepository extends JpaRepository<OperationalCost, Long> {

    @Query("""
        SELECT COALESCE(SUM(o.amount), 0)
        FROM OperationalCost o
        WHERE o.costDate BETWEEN :startDate AND :endDate
    """)
    BigDecimal sumAmountBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
        SELECT COALESCE(SUM(o.amount), 0)
        FROM OperationalCost o
        WHERE o.costDate BETWEEN :startDate AND :endDate
        AND o.category = :category
    """)
    BigDecimal sumAmountByCategoryBetween(
            @Param("category") OperationalCostCategory category,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    List<OperationalCost> findByCostDateBetween(LocalDate startDate, LocalDate endDate);
}