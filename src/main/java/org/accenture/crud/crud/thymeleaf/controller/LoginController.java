package org.accenture.crud.crud.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/showMyLoginPage")
	public String showMyLoginPage()
	{
		return "/login/custom-page";
	}
	
	@GetMapping("/access-denied")
	public String showAccessDenied()
	{
		
		return "/login/access-denied";
	}
	
	
	@GetMapping("/")
	public String redirectToList()
	{
		
		return "redirect:/employees/list";
	}
	

}
