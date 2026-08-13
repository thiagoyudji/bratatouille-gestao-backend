package br.com.bratatouille.management.sales.repository;

import br.com.bratatouille.management.sales.entity.SalesOrder;
import br.com.bratatouille.management.sales.entity.SalesPaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {

    Optional<SalesOrder> findByPaymentProviderTransactionId(String paymentProviderTransactionId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT s
        FROM SalesOrder s
        WHERE s.id = :id
    """)
    java.util.Optional<SalesOrder> findByIdForUpdate(@Param("id") Long id);

    @Query("""
        SELECT COALESCE(SUM(s.totalAmount), 0)
        FROM SalesOrder s
        WHERE s.saleDate BETWEEN :startDate AND :endDate
        AND s.paymentStatus = :paymentStatus
    """)
    BigDecimal sumTotalAmountBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("paymentStatus") SalesPaymentStatus paymentStatus
    );

    @Query("""
        SELECT COALESCE(SUM(s.totalCost), 0)
        FROM SalesOrder s
        WHERE s.saleDate BETWEEN :startDate AND :endDate
        AND s.paymentStatus = :paymentStatus
    """)
    BigDecimal sumTotalCostBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("paymentStatus") SalesPaymentStatus paymentStatus
    );

    @Query("""
        SELECT COALESCE(SUM(s.grossProfit), 0)
        FROM SalesOrder s
        WHERE s.saleDate BETWEEN :startDate AND :endDate
        AND s.paymentStatus = :paymentStatus
    """)
    BigDecimal sumGrossProfitBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("paymentStatus") SalesPaymentStatus paymentStatus
    );

    @Query("""
        SELECT COUNT(s)
        FROM SalesOrder s
        WHERE s.saleDate BETWEEN :startDate AND :endDate
        AND s.paymentStatus = :paymentStatus
    """)
    Long countOrdersBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("paymentStatus") SalesPaymentStatus paymentStatus
    );

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
        AND i.salesOrder.paymentStatus = :paymentStatus
        GROUP BY i.item.id, i.item.name
        ORDER BY SUM(i.grossProfit) DESC
    """)
    List<Object[]> findProductPerformanceBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("paymentStatus") SalesPaymentStatus paymentStatus
    );

    @Query("""
    SELECT COUNT(i)
    FROM SalesOrderItem i
    WHERE i.salesOrder.saleDate BETWEEN :startDate AND :endDate
    AND i.salesOrder.paymentStatus = :paymentStatus
    AND i.costIncomplete = true
""")
    Long countCostIncompleteItemsBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("paymentStatus") SalesPaymentStatus paymentStatus
    );
}
