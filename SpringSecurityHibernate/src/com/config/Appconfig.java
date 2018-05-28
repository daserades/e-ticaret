package com.config;

import java.util.Properties;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.model.Authorities;
import com.model.Users;
import com.mysql.cj.jdbc.Driver;
import static org.hibernate.cfg.Environment.*;
@Configuration
@PropertySource("classpath:resources/db.properties")
@EnableTransactionManagement
@ComponentScan(basePackages="com.model")
@ComponentScan(basePackages="com.service")
public class Appconfig {

	@Autowired
	private Environment env;
	
	@Bean
	public LocalSessionFactoryBean getSession(){
		LocalSessionFactoryBean factoryBean=new LocalSessionFactoryBean();
		Properties prop=new Properties();
		
		prop.put(DRIVER,env.getProperty("mysql.DriverName"));
		prop.put(URL, env.getProperty("mysql.jdbcUrl"));
		prop.put(PASS, env.getProperty("mysql.password"));
		prop.put(USER, env.getProperty("mysql.user"));
		
		prop.put(SHOW_SQL, env.getProperty("hibernate.show_sql"));
		prop.put(HBM2DDL_AUTO, env.getProperty("hibernate.hbm2dd1.auto"));
		
		   // Setting C3P0 properties
	    prop.put(C3P0_MIN_SIZE, env.getProperty("hibernate.c3p0.min_size"));
	    prop.put(C3P0_MAX_SIZE, env.getProperty("hibernate.c3p0.max_size"));
	    prop.put(C3P0_ACQUIRE_INCREMENT, env.getProperty("hibernate.c3p0.acquire_increment"));
	    prop.put(C3P0_TIMEOUT, env.getProperty("hibernate.c3p0.timeout"));
	    prop.put(C3P0_MAX_STATEMENTS, env.getProperty("hibernate.c3p0.max_statements"));
	    
	    factoryBean.setHibernateProperties(prop);
	    //factoryBean.setPackagesToScan("com.model","com.service");
	    factoryBean.setAnnotatedClasses(Authorities.class,Users.class);
	    
	    return factoryBean;
	}
	
	@Bean
	public HibernateTransactionManager transactionManager(){
		HibernateTransactionManager tsm=new HibernateTransactionManager();
		tsm.setSessionFactory(getSession().getObject());
		
		return transactionManager();
	}
}
