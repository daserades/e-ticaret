package com.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dao.UserDetailsDao;
@Service
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	private UserDetailsDao userDetailsDao;
	
	UserBuilder builder;
	
	@Transactional
	@Override
	public UserDetails loadByUserName(String username) {
		
		User user =userDetailsDao.findByUserName(username);
		
		
		if(user!=null){
			builder=org.springframework.security.core.userdetails.User.withUsername(username);
			builder.disabled(!user.isEnabled());
			builder.password(user.getPassword());
			String[] authorities=user.getAuthorities().stream().map(a->a.getAuthority()).toArray(String[]::new);
			
			builder.authorities(authorities);
		}
		else{
			throw new UsernameNotFoundException("Username not foun");
		}
		return builder.build();
	}

}
