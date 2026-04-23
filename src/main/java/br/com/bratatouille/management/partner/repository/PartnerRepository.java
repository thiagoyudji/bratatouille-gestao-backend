package br.com.bratatouille.management.partner.repository;

import br.com.bratatouille.management.partner.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
}