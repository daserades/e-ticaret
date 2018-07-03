package java.config;

import java.dao.AccountDAO;
import java.dao.OrderDAO;
import java.dao.ProductDAO;
import java.dao.daoImpl.AccountDaoImpl;
import java.dao.daoImpl.OrderDaoImpl;
import java.dao.daoImpl.ProductDaoImpl;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan("java")
@EnableTransactionManagement
@PropertySource("classpath:property.properties")
public class ApplicationContextConfig {

	@Autowired
	private Environment env;
	
	//message dosyasindan validasyon 
	@Bean
	public ResourceBundleMessageSource messageSource(){
		ResourceBundleMessageSource rb=new ResourceBundleMessageSource();
		rb.setBasenames(new String[]{"messages/validator"});
		return rb;
	}
	
	@Bean
	public InternalResourceViewResolver getViewResolver(){
		InternalResourceViewResolver viewResolver=new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	//dosya yukleme ayarlari
	@Bean
	public CommonsMultipartResolver multipartResolver(){
		CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver();
		
		commonsMultipartResolver.setMaxUploadSize(10000000);
		commonsMultipartResolver.setMaxUploadSizePerFile(10000);
		return commonsMultipartResolver;
	}
	
	@Bean(name="dataSource")
	public DataSource getDataSource(){
		DriverManagerDataSource dataSource=new DriverManagerDataSource();
		
		dataSource.setDriverClassName(env.getProperty("ds.database-driver"));
		dataSource.setUrl(env.getProperty("ds.database-url"));
		dataSource.setUsername(env.getProperty("ds.database-username"));
		dataSource.setPassword(env.getProperty("ds.database-password"));
		 
		return dataSource;
	}
	
	//hibernate configuration
	@Autowired
	@Bean
	public SessionFactory getSessionFactory(DataSource dataSource) throws Throwable{
		Properties prop=new Properties();
		
		prop.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		prop.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		prop.put("current_session_context_class", env.getProperty("current_session_context_class"));
		
		LocalSessionFactoryBean factoryBean=new LocalSessionFactoryBean();
		 // Package contain entity classes
        factoryBean.setPackagesToScan(new String[] { "org.o7planning.springmvcshoppingcart.entity" });
        factoryBean.setDataSource(dataSource);
        factoryBean.setHibernateProperties(prop);
        factoryBean.afterPropertiesSet();
        //
        SessionFactory sf = factoryBean.getObject();
        System.out.println("## getSessionFactory: " + sf);
        return sf;
		
		
	}
	
	@Autowired
	@Bean
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory){
		HibernateTransactionManager transactionManager=new HibernateTransactionManager(sessionFactory);
		return transactionManager;
	}
	
	@Bean(name="accountDAO")
	public AccountDAO getApplicationDAO(){
		return new AccountDaoImpl();
	}
	
	@Bean(name="productDAO")
	public ProductDAO getProductDAO(){
		return new ProductDaoImpl();
	}
	
	@Bean(name="orderDAO")
	public OrderDAO getOrderDAO(){
		return new OrderDaoImpl();
	}
	
	
}
