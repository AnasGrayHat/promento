package promento.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import promento.dao.CompanyRepository;
import promento.entities.Company;

@Controller
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private CompanyRepository companyRepository ;
	
	
	@Value("${dir.images.logos}")
	private String logosDir;
	
	
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(       Date.class,     
	                         new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true, 10));   
	}
	
	
	
	
	@RequestMapping("add_company")
	public String addCompany(Model model)
	{
//		 new Company("zzz" , "legal" , "anas" ,  new Date(), "lkjsd" , 12 , 25 , "dsdsds")
		
		Company newCompany = new Company();
		newCompany.setDateCreation(new Date());
		model.addAttribute("company",newCompany);

		return "company/add_company";
		
	}
	
	@RequestMapping(  value = "/save_company" , method=RequestMethod.POST)
	public String saveCompany( Model model ,
			@ModelAttribute("company") @Valid Company company ,
			BindingResult bendingResult,
			@RequestParam("logo") MultipartFile logo_file ) throws IllegalStateException, IOException {
		
		if( bendingResult.hasErrors() ) {
			model.addAttribute("company", company );
			return "company/add_company";
		}
		
		
		
		
		Company savedCompany = companyRepository.save( company );

		String filename = savedCompany.getId().toString();
		savedCompany.setlogoFileName(filename);
		companyRepository.save( savedCompany );

			
			
		if(!new File(logosDir).exists())
			new File(logosDir).mkdirs();
		
		
		if(!logo_file.isEmpty()) 
		{

			logo_file.transferTo(new File(logosDir + File.separator + filename));
			
			
		}
		
		
		
		return "redirect:companies";
	}
	
	@RequestMapping("/companies")
	public String getCompanies(Model model)
	{
		List<Company> companiesList = companyRepository.findAll();
		model.addAttribute("companies" , companiesList);
		return "company/companies";
		
	}
	
	
	@RequestMapping("/delete_company")
	public String deleteCompany(Long id)
	{
		
		companyRepository.delete(id);
		
		return "redirect:companies";
		
	}
	
	
	@RequestMapping("/edit_company")
	public String editCompany(Model model , Long id)
	{
		
		Company company = companyRepository.findOne(id);
		
		model.addAttribute("company", company);

		return "company/edit_company";
		
	}
	
	

	
	@RequestMapping(  value = "/update_company" , method=RequestMethod.POST)
	public String updateCompany( Model model ,
			@ModelAttribute("company") @Valid Company company ,
			BindingResult bendingResult ,
			@RequestParam("logo") MultipartFile logo_file ) throws IllegalStateException, IOException {
		
		if( bendingResult.hasErrors() ) {
			model.addAttribute("company", company );
			return "company/edit_company";
		}
		
		

		String filename = company.getId().toString();
		companyRepository.save( company );

			
			
		if(!new File(logosDir).exists())
			new File(logosDir).mkdirs();
		
		
		if(!logo_file.isEmpty()) 
		{
			logo_file.transferTo(new File(logosDir + File.separator + filename));
			
			
		}
		
		
		return "redirect:companies";
	}

	
	@RequestMapping(value="/logos" , produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getLogo( Long logoId ,HttpServletRequest request) throws IOException
	{

		Path path = null;
		byte[] data ;
		
		
		String rpath = logosDir + File.separator + logoId;;

		path = Paths.get(rpath);
		if(path.toFile().exists())
		{
			 
			data = Files.readAllBytes(path);
		}
		else
		{
			throw new ResourceNotFoundException();
		}
		

		
		return data;
	}
	
	
	@RequestMapping("/finance")
	public String finance(Model model , Long companyId)
	{
		Company company = companyRepository.findOne(companyId);
		model.addAttribute("company", company);
		return "company/finance";
		
	}
	
	@RequestMapping("/mentor")
	public String mentor(Model model , Long companyId)
	{
		Company company = companyRepository.findOne(companyId);
		model.addAttribute("company", company);
		return "company/mentor";
		
	}
	
}
