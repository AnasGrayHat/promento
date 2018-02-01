package promento.services;



import java.security.KeyStore.Entry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import promento.dao.IndicatorRepository;
import promento.dao.CompanyRepository;
import promento.entities.Company;
import promento.entities.Indicator;
import promento.entities.indicators.ChargesEstimated;
import promento.entities.indicators.ChargesReal;
import promento.entities.indicators.Treasury;
import promento.entities.indicators.TurnOverEstimated;
import promento.entities.indicators.TurnOverReal;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


@RestController
@RequestMapping("/indicator")
public class IndicatorRestController {

	  private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CompanyRepository companyRepository ;
	
	@Autowired
	private IndicatorRepository indicatorRepository ;
	
	
	@RequestMapping("budget/{company_id}/{year}")
	public List<HashMap<String, Indicator>> getBudget(@PathVariable(value="company_id") Long companyID ,
							 	   @PathVariable(value="year") 		 int  year)
	{
		
			if(! companyRepository.exists(companyID))
				return null;
		
		
		
		
		List<HashMap<String, Indicator>> budgetsList = new ArrayList<>();

		
		HashMap<String, Indicator> mapTurnOverEstimated = new HashMap<>();
		HashMap<String, Indicator> mapChargesEstimated = new HashMap<>();

		
		

		List<Indicator> indicatorsTurnOverEstimated =  indicatorRepository.getTurnOverEstimatedByCompanyByYear(companyID , year);
		List<Indicator> indicatorsChargesEstimated =  indicatorRepository.getChargesEstimatedByCompanyByYear(companyID , year);

		
		if(indicatorsChargesEstimated.isEmpty())//then there is no budget for this year we should create it 
		{
			
			populateIndicators(companyID, year);
			
			
			indicatorsTurnOverEstimated =  indicatorRepository.getTurnOverEstimatedByCompanyByYear(companyID , year);
			indicatorsChargesEstimated =  indicatorRepository.getChargesEstimatedByCompanyByYear(companyID , year);

		}
		
		
			
			for (Indicator indicator : indicatorsTurnOverEstimated) {
				
				String month = new SimpleDateFormat("MMMM").format(indicator.getDate());
				mapTurnOverEstimated.put(month.toLowerCase(), indicator);
			}
			
			
			for (Indicator indicator : indicatorsChargesEstimated) {
				
				String month = new SimpleDateFormat("MMMM").format(indicator.getDate());
				mapChargesEstimated.put(month.toLowerCase(), indicator);
			}
		
			budgetsList.add(mapTurnOverEstimated);
			budgetsList.add(mapChargesEstimated);
			
			
		return budgetsList;
		
		
		
		
	}

	
	
	@RequestMapping("perfermance/{company_id}/{year}")
	public List<HashMap<String, Indicator>> getPerfermance(@PathVariable(value="company_id") Long companyID ,
							 	   @PathVariable(value="year") 		 int  year)
	{
		
		
		if(! companyRepository.exists(companyID))
			return null;
		
		
		List<HashMap<String, Indicator>> budgetsList = new ArrayList<>();

		
		
		
		HashMap<String, Indicator> mapTurnOverReal = new HashMap<>();
		HashMap<String, Indicator> mapChargesReal = new HashMap<>();
		HashMap<String, Indicator> mapTreasury = new HashMap<>();

		
		

		List<Indicator> indicatorsTurnOverReal =  indicatorRepository.getTurnOverRealByCompanyByYear(companyID , year);
		List<Indicator> indicatorsChargesReal =  indicatorRepository.getChargesRealByCompanyByYear(companyID , year);
		List<Indicator> indicatorsTreasury =  indicatorRepository.getTreasuryByCompanyByYear(companyID , year);

		
		if(indicatorsChargesReal.isEmpty())//then there is no budget for this year we should create it 
		{
			
			populateIndicators(companyID, year);
			
			indicatorsTurnOverReal =   indicatorRepository.getTurnOverRealByCompanyByYear(companyID , year);
			indicatorsChargesReal  =   indicatorRepository.getChargesRealByCompanyByYear(companyID , year);
			indicatorsTreasury 	   =   indicatorRepository.getTreasuryByCompanyByYear(companyID , year);

			
		}
		
		
			
			for (Indicator indicator : indicatorsTurnOverReal) {
				
				String month = new SimpleDateFormat("MMMM").format(indicator.getDate());
				mapTurnOverReal.put(month.toLowerCase(), indicator);
			}
			
			
			for (Indicator indicator : indicatorsChargesReal) {
				
				String month = new SimpleDateFormat("MMMM").format(indicator.getDate());
				mapChargesReal.put(month.toLowerCase(), indicator);
			}
		
			for (Indicator indicator : indicatorsTreasury) {
				
				String month = new SimpleDateFormat("MMMM").format(indicator.getDate());
				mapTreasury.put(month.toLowerCase(), indicator);
			}
			
			
			budgetsList.add(mapTurnOverReal);
			budgetsList.add(mapChargesReal);
			budgetsList.add(mapTreasury);

			
		return budgetsList;
		
		
		
		
	}
	
	
	
	
	
	@RequestMapping(value = "save" ,method = RequestMethod.POST , consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity  saveIndicators(@RequestBody List<HashMap<String, Indicator>> indicators)
	{
		
		for (HashMap<String, Indicator> map : indicators) {
			
			
			for (Indicator indicator : map.values()) {
			Indicator ind = indicatorRepository.findOne(indicator.getId());
			ind.setValue(indicator.getValue());
			
			indicatorRepository.save(ind);
			}
		}
		
		
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	
	
	@RequestMapping(value = "test" ,method = RequestMethod.POST , consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity testIndicators(@RequestBody List<Indicator> indicators)
	{
			
		return new ResponseEntity<Indicator>(indicators.get(0) ,HttpStatus.OK);
		
	}
	
	
	
	private void populateIndicators(Long companyID, int year)
	{
		Company company  = companyRepository.findOne(companyID);
		
		Calendar calendar = new GregorianCalendar(year, Calendar.JANUARY, 1);
		
//		Treasury treasury  = null;
//		
//		ChargesReal chargesReal = null;
//		ChargesEstimated chargesEstimated = null;
//		
//		TurnOverReal turnOverReal = null;
//		TurnOverEstimated turnOverEstimated = null;
		
		//for each  month
		for (int i = 1; i <= 12; i++) {
			
			Indicator[] indicators  = {new Treasury(null, new Date(calendar.getTimeInMillis()), company),
			                           new ChargesReal(null, new Date(calendar.getTimeInMillis()), company),
			                           new ChargesEstimated(null, new Date(calendar.getTimeInMillis()), company),
			                           new TurnOverReal(null, new Date(calendar.getTimeInMillis()), company),
			                           new TurnOverEstimated(null, new Date(calendar.getTimeInMillis()), company)};
			
			
			
			for (int j = 0; j < indicators.length; j++) {
				company.getIndicators().add(indicators[j]);
				indicatorRepository.save(indicators[j]);


			}
	
			calendar.add(Calendar.MONTH,1);

		}
		
		companyRepository.save(company);
		
		
	}
	
	
	@RequestMapping("years/{company_id}")
	public ResponseEntity getYears(@PathVariable(value="company_id") Long companyID)
	{
		
			
		return new ResponseEntity<List<Integer>>(indicatorRepository.getYears(companyID) ,HttpStatus.OK);
		
	}


	
}
