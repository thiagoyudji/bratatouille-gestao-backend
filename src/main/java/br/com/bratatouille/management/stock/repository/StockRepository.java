package br.com.bratatouille.management.stock.repository;

import br.com.bratatouille.management.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByItemId(Long itemId);
}