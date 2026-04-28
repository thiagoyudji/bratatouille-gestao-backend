package br.com.bratatouille.management.financialClosing.repository;

import br.com.bratatouille.management.financialClosing.entity.FinancialClosing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface FinancialClosingRepository extends JpaRepository<FinancialClosing, Long> {

    Optional<FinancialClosing> findByStartDateAndEndDate(LocalDate startDate, LocalDate endDate);

    @Query("""
        SELECT COUNT(fc) > 0
        FROM FinancialClosing fc
        WHERE fc.startDate <= :endDate
        AND fc.endDate >= :startDate
    """)
    boolean existsOverlapping(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}