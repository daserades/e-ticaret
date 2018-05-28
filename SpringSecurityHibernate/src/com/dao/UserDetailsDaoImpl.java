package com.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;


@Repository
public class UserDetailsDaoImpl implements UserDetailsDao{

	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public User findByUserName(String username) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().get(User.class, username);
	}

}
