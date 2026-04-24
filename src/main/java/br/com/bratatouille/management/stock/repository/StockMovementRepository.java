package br.com.bratatouille.management.stock.repository;

import br.com.bratatouille.management.stock.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findAllByOrderByCreatedAtDesc();
}