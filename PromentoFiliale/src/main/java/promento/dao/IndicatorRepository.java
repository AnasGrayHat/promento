package promento.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import promento.entities.Indicator;




public interface IndicatorRepository extends JpaRepository<Indicator, Long> {

    @Query("SELECT ind FROM Treasury ind WHERE ind.company.id = (:company_id) AND  YEAR(ind.date) = (:year)")
	public List<Indicator> getTreasuryByCompanyByYear(@Param("company_id") Long company_id  ,@Param("year") int year);

    @Query("SELECT ind FROM TurnOverReal ind WHERE ind.company.id = (:company_id) AND  YEAR(ind.date) = (:year)")
	public List<Indicator> getTurnOverRealByCompanyByYear(@Param("company_id") Long company_id  ,@Param("year") int year);
    
    @Query("SELECT ind FROM TurnOverEstimated ind WHERE ind.company.id = (:company_id) AND  YEAR(ind.date) = (:year)")
	public List<Indicator> getTurnOverEstimatedByCompanyByYear(@Param("company_id") Long company_id  ,@Param("year") int year);
    
    @Query("SELECT ind FROM ChargesReal ind WHERE ind.company.id = (:company_id) AND  YEAR(ind.date) = (:year)")
	public List<Indicator> getChargesRealByCompanyByYear(@Param("company_id") Long company_id  ,@Param("year") int year);
    
    @Query("SELECT ind FROM ChargesEstimated ind WHERE ind.company.id = (:company_id) AND  YEAR(ind.date) = (:year)")
	public List<Indicator> getChargesEstimatedByCompanyByYear(@Param("company_id") Long company_id  ,@Param("year") int year);
    
    @Query("SELECT DISTINCT YEAR (ind.date) AS financial_date  FROM Indicator ind WHERE ind.company.id = (:company_id) ORDER BY financial_date")
   	public List<Integer> getYears(@Param("company_id") Long company_id);
}
