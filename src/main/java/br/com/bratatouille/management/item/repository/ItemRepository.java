package br.com.bratatouille.management.item.repository;

import br.com.bratatouille.management.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    boolean existsByNameIgnoreCase(String name);

    java.util.List<Item> findByActiveTrueAndNameContainingIgnoreCase(String name);

    java.util.Optional<Item> findByNameIgnoreCase(String name);
}
