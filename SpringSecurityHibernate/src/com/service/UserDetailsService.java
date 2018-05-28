package com.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {

	public UserDetails loadByUserName(String username);
}
