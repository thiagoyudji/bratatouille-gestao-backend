package br.com.bratatouille.management.lot.repository;

import br.com.bratatouille.management.lot.entity.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {

    Optional<Lot> findByProductionId(Long productionId);

    List<Lot> findByItemIdOrderByExpirationDateAsc(Long itemId);

    List<Lot> findByExpirationDateBetweenOrderByExpirationDateAsc(LocalDate startDate, LocalDate endDate);
}