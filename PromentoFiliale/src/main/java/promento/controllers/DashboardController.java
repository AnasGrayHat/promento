package promento.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller


public class DashboardController {

	
//	@Secured(value="ROLE_ADMIN")
	@RequestMapping("/")
	public String dashboard()
	{
		
		return "dashboard";
		
		
	}
	
	
		
    @RequestMapping("/login")
    public String login(){
    	
        return "login";
    }
    

	
	
}
