package com.climatesystem.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.climatesystem.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// @formatter:off
		http
			.authorizeRequests()
				.antMatchers(
						"/",
						"/about")
				.permitAll()
				.antMatchers(
						"/js/*",
						"/css/*",
						"/img/*",
						"/img/svg/*",
						"/ws/*")
				.permitAll()
				.antMatchers(
						"/register")
				.hasRole("ADMIN")
				.antMatchers(
						"/dashboard")
				.authenticated()
				.anyRequest()
				.denyAll()
				.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/", true)
				.permitAll()
				.and()
			.logout()
				.permitAll();
		// @formatter:on
	}
}
