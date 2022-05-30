package com.ohrs.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RoleController {

	@RequestMapping("/CustomerHome")
	public ModelAndView custPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("CustomerHome.html");
		return mv;
	}

	@RequestMapping("/AdminHome")
	public ModelAndView adminPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("AdminHome.html");
		return mv;
	}

	@RequestMapping("/OwnerHome")
	public ModelAndView ownerPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("OwnerHome.html");
		return mv;
	}

}
