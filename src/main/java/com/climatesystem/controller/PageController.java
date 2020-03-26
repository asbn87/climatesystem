package com.climatesystem.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {

	@Value("${message.error.forbidden}")
	private String accessDeniedMessage;
	
	@RequestMapping("/")
	ModelAndView home(ModelAndView modelAndView) {
		
		modelAndView.setViewName("app.homepage");
		
		return modelAndView;
	}
	
	@RequestMapping("/about")
	String about() {
		return "app.about";
	}
	
	@RequestMapping("/403")
	ModelAndView accessDenied(ModelAndView modelAndView) {
		
		modelAndView.getModel().put("message", accessDeniedMessage);
		modelAndView.setViewName("app.message");
		return modelAndView;
	}
}
