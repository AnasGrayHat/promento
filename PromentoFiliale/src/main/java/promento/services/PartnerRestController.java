package promento.services;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import promento.dao.PartnerRepository;
import promento.entities.Partner;



@RestController
@RequestMapping("/RestPartner")
public class PartnerRestController {



	
	@Autowired
	private PartnerRepository partnerRepository ;
	
	

	
	@RequestMapping("partners")
	public ResponseEntity getPartners()
	{
		
			
		return new ResponseEntity<List<Partner>>(partnerRepository.findAll() ,HttpStatus.OK);
		
	}

	
	
}
