package com.ohrs.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

	@GetMapping("/all")
	public ModelAndView allAccess() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home.html");
		return mv;
	}

	//login
	@GetMapping("/login")
	public ModelAndView loginPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login.html");
		return mv;
	}

	// registration 
	@GetMapping("/registration")
	public ModelAndView registrationPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("registration.html");
		return mv;
	}

}
