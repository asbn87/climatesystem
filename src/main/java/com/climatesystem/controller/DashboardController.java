package com.climatesystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {
	
	@RequestMapping(value="/dashboard")
	public ModelAndView showProfile(ModelAndView modelAndView) {
		
		modelAndView.setViewName("app.dashboard");
		return modelAndView;
	}
}
