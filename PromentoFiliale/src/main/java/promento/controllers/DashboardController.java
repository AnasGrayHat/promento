package promento.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import promento.dao.CompanyRepository;

@Controller


public class DashboardController {

	@Autowired
	private CompanyRepository companyRepository ;
	
	private @Autowired HttpServletRequest request;

//	@Secured(value="ROLE_ADMIN")
	@RequestMapping("/")
	public String dashboard()
	{
		
		if(request.getAttribute("logged") == null || !(boolean)request.getAttribute("logged"))
			return "login";
		
//		model.addAttribute("companies", companyRepository.findAll());
		return "dashboard";
		
		
	}
	
	
		
    @RequestMapping(value = "/login" , method=RequestMethod.POST)
    public String login(){
    	
    	
    	if(request.getAttribute("username") =="admin" && request.getAttribute("password")  == "admin")
    	{
    		 request.setAttribute("logged", true);
    		 return "redirect:company/companies";

    	}
    		
        return "login";
    }
    

	
	
}
