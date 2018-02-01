package promento.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import promento.entities.Partner;


public interface PartnerRepository extends JpaRepository<Partner, Long> {

}
