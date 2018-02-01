package promento.services;




import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import promento.dao.PartnerRepository;
import promento.entities.Company;
import promento.entities.Indicator;
import promento.entities.Mentoring;
import promento.entities.Partner;
import promento.dao.CompanyRepository;
import promento.dao.MentoringRepository;



@RestController
@RequestMapping("/Mentoring")
public class MentoringRestController {



	
	@Autowired
	private MentoringRepository mentoringRepository ;
	
	@Autowired
	private PartnerRepository partnerRepository ;

	
	@Autowired
	private CompanyRepository companyRepository ;
	
	
	
	@RequestMapping("/mentors/{company_id}")
	public ResponseEntity getMentors(@PathVariable(value="company_id") Long companyID )
	{
		
			
		return new ResponseEntity<List<Mentoring>>(mentoringRepository.getMentorsByCompany(companyID) ,HttpStatus.OK);
		
	}

	@RequestMapping("save/{company_id}/{partner_id}")
	public ResponseEntity saveMentoring(@PathVariable(value="company_id") Long companyID ,
										@PathVariable(value="partner_id") Long partnerID ,
										@RequestBody Mentoring mentoring)
	{
		Company company = companyRepository.findOne(companyID);
		Partner partner = partnerRepository.findOne(partnerID);

		
		mentoring.setMentor(partner);
		mentoring.setCompany(company);

		mentoringRepository.save(mentoring);
		
		
		return new ResponseEntity<Mentoring>(mentoring ,HttpStatus.OK);
		
	}
	
	@RequestMapping("delete/{mentoring_id}")
	public ResponseEntity deleteMentoring(@PathVariable(value="mentoring_id") Long mentoringID)
	{
	

		mentoringRepository.delete(mentoringID);
		
		
		return  ResponseEntity.status(HttpStatus.OK).body(null);
		
	}
	
	
	
}
