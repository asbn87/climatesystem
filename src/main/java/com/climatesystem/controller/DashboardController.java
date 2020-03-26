package com.climatesystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.climatesystem.model.UserModel;
import com.climatesystem.service.UserService;

@Controller
public class DashboardController {
	
	@Autowired
	private UserService userService;
	
	private UserModel getUser() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String user = auth.getName();
		
		return userService.get(user);
	}
	
	@RequestMapping(value="/dashboard")
	public ModelAndView showProfile(ModelAndView modelAndView) {
		
		modelAndView.setViewName("app.dashboard");
		return modelAndView;
	}
}
