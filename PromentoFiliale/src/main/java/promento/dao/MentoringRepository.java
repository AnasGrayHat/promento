package promento.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import promento.entities.Mentoring;



public interface MentoringRepository extends JpaRepository<Mentoring, Long> {
	
    @Query("SELECT ment FROM Mentoring ment WHERE ment.company.id = (:company_id)")
	public List<Mentoring> getMentorsByCompany(@Param("company_id") Long company_id );


}
