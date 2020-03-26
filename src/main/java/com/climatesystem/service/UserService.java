package com.climatesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.climatesystem.model.UserDao;
import com.climatesystem.model.UserModel;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;
	
	public void register(UserModel user) {
		user.setRole("ROLE_USER");
		userDao.save(user);
	}
	
	public void save(UserModel user) {
		userDao.save(user);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserModel user = userDao.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		
		List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole());
		
		return new User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, auth);
	}
	
	public UserModel get(String username) {
		return userDao.findByUsername(username);
	}
}
