package br.com.bratatouille.management.sales.repository;

import br.com.bratatouille.management.sales.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {

    @Query("""
        SELECT COALESCE(SUM(s.totalAmount), 0)
        FROM SalesOrder s
        WHERE s.saleDate BETWEEN :startDate AND :endDate
    """)
    BigDecimal sumTotalAmountBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("""
        SELECT COALESCE(SUM(s.totalCost), 0)
        FROM SalesOrder s
        WHERE s.saleDate BETWEEN :startDate AND :endDate
    """)
    BigDecimal sumTotalCostBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("""
        SELECT COALESCE(SUM(s.grossProfit), 0)
        FROM SalesOrder s
        WHERE s.saleDate BETWEEN :startDate AND :endDate
    """)
    BigDecimal sumGrossProfitBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("""
        SELECT COUNT(s)
        FROM SalesOrder s
        WHERE s.saleDate BETWEEN :startDate AND :endDate
    """)
    Long countOrdersBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("""
        SELECT
            i.item.id,
            i.item.name,
            SUM(i.quantity),
            SUM(i.totalPrice),
            SUM(i.totalCost),
            SUM(i.grossProfit)
        FROM SalesOrderItem i
        WHERE i.salesOrder.saleDate BETWEEN :startDate AND :endDate
        GROUP BY i.item.id, i.item.name
        ORDER BY SUM(i.grossProfit) DESC
    """)
    List<Object[]> findProductPerformanceBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}