package br.com.bratatouille.management.sellableStock.repository;

import br.com.bratatouille.management.sellableStock.entity.SellableStock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SellableStockRepository extends JpaRepository<SellableStock, Long> {

    Optional<SellableStock> findByItemId(Long itemId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM SellableStock s WHERE s.item.id = :itemId")
    Optional<SellableStock> findByItemIdForUpdate(@Param("itemId") Long itemId);
}