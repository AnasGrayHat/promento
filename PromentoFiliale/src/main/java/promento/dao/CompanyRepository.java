package promento.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import promento.entities.Company;


public interface CompanyRepository extends JpaRepository<Company, Long> {

}
