package promento.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import promento.dao.PartnerRepository;
import promento.entities.Partner;

@Controller
@RequestMapping("/partner")
public class PartnerController {

	@Autowired
	private PartnerRepository partnerRepository ;
	
	
	@Value("${dir.images.partner.photos}")
	private String photosDir;
	
	
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(       Date.class,     
	                         new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true, 10));   
	}
	
	
	
	
	@RequestMapping("/add_partner")
	public String addPartner(Model model)
	{
		
		Partner newPartner = new Partner();
		
		model.addAttribute("partner",newPartner);

		return "partner/add_partner";
		
	}
	
	@RequestMapping(  value = "/save_partner" , method=RequestMethod.POST)
	public String savePartner( Model model ,
			@ModelAttribute("partner") @Valid Partner partner ,
			BindingResult bendingResult,
			@RequestParam("photo") MultipartFile photo_file ) throws IllegalStateException, IOException {
		
		if( bendingResult.hasErrors() ) {
			model.addAttribute("partner", partner );
			return "partner/add_partner";
		}
		
		
		
		
		Partner savedPartner = partnerRepository.save( partner );

		String filename = savedPartner.getId().toString();
		savedPartner.setPhotoFileName(filename);
		partnerRepository.save( savedPartner );

			
			
		if(!new File(photosDir).exists())
			new File(photosDir).mkdirs();
		
		
		if(!photo_file.isEmpty()) 
		{

			photo_file.transferTo(new File(photosDir + File.separator + filename));
			
			
		}
		
		
		
		return "redirect:partners";
	}
	
	@RequestMapping("/partners")
	public String getPartners(Model model)
	{
		List<Partner> partnersList = partnerRepository.findAll();
		model.addAttribute("partners" , partnersList);
		return "partner/partners";
		
	}
	
	
	@RequestMapping("/delete_partner")
	public String deletePartner(Long id)
	{
		
		partnerRepository.delete(id);
		
		return "redirect:partners";
		
	}
	
	
	@RequestMapping("/edit_partner")
	public String editPartner(Model model , Long id)
	{
		
		Partner partner = partnerRepository.findOne(id);
		
		model.addAttribute("partner", partner);

		return "partner/edit_partner";
		
	}
	
	

	
	@RequestMapping(  value = "/update_partner" , method=RequestMethod.POST)
	public String updatePartner( Model model ,
			@ModelAttribute("partner") @Valid Partner partner ,
			BindingResult bendingResult ,
			@RequestParam("photo") MultipartFile logo_file ) throws IllegalStateException, IOException {
		
		if( bendingResult.hasErrors() ) {
			model.addAttribute("partner", partner );
			return "partner/edit_partner";
		}
		
		

		String filename = partner.getId().toString();
		partnerRepository.save( partner );

			
			
		if(!new File(photosDir).exists())
			new File(photosDir).mkdirs();
		
		
		if(!logo_file.isEmpty()) 
		{
			logo_file.transferTo(new File(photosDir + File.separator + filename));
			
			
		}
		
		
		return "redirect:partners";
	}

	
	@RequestMapping(value="/photos" , produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhoto( Long photoId ,HttpServletRequest request) throws IOException
	{

		Path path = null;
		byte[] data ;
		
		
		String rpath = photosDir + File.separator + photoId;;

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
	
	
}
