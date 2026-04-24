package br.com.bratatouille.management.production.repository;

import br.com.bratatouille.management.production.entity.Production;
import br.com.bratatouille.management.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionRepository extends JpaRepository<Production, Long> {
}