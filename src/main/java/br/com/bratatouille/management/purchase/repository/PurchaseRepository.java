package br.com.bratatouille.management.purchase.repository;

import br.com.bratatouille.management.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByPurchaseDateBetween(LocalDate startDate, LocalDate endDate);
}