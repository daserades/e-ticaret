package config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import dao.MyUserAccountDAO;

@Configuration
@EnableSocial
@PropertySource("classpath:social-cfg.properties")
public class SocialConfig implements SocialConfigurer{

	private boolean autoSignUp=false;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private MyUserAccountDAO myUserAccountDAO;
	
	
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig,
			Environment env) {
		//twitter
		TwitterConnectionFactory tFactory=new TwitterConnectionFactory(env.getProperty("twitter.consumer.key"),
				env.getProperty("twitter.consumer.secret"));
		cfConfig.addConnectionFactory(tFactory);
		
		//facebook
		FacebookConnectionFactory fFactory=new FacebookConnectionFactory(
				env.getProperty("facebook.app.id"),
				env.getProperty("facebook.app.id"));
		fFactory.setScope(env.getProperty("facebook.scope"));
		
		//linkedin
		
		
	}

	@Override
	public UserIdSource getUserIdSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		// TODO Auto-generated method stub
		return null;
	}

}
