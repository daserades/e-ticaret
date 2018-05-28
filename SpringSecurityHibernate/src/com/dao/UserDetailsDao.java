package com.dao;

import org.springframework.security.core.userdetails.User;

import com.model.Users;

public interface UserDetailsDao {

	public User findByUserName(String username);
}
