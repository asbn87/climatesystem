package com.climatesystem.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.climatesystem.model.UserModel;
import com.climatesystem.service.UserService;

@Controller
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Value("${message.registration.confirmed}")
	private String registrationConfirmedMessage;
	
	@RequestMapping("/login")
	String login() {
		return "app.login";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	ModelAndView register(ModelAndView modelAndView) {
		
		UserModel user = new UserModel();
		
		modelAndView.getModel().put("user", user);
		modelAndView.setViewName("app.register");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	ModelAndView register(ModelAndView modelAndView, @ModelAttribute(value="user") @Valid UserModel user, BindingResult result) {
		
		modelAndView.setViewName("app.register");
		
		if(!result.hasErrors()) {
			userService.register(user);

			modelAndView.getModel().put("message", registrationConfirmedMessage);
			modelAndView.setViewName("app.message");
		}
		
		return modelAndView;
	}
}
