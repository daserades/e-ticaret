package java.config;

import java.authentication.MyDBAuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	MyDBAuthenticationService myDBAuthenticationService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//databasedeki kullanýcýlar
		auth.userDetailsService(myDBAuthenticationService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		//the pages requires login es employee or manager
		//if not login redirect login page
		http.authorizeRequests().antMatchers("/orderList","/order","accountInfo")
		.access("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER')");
		
		//for manager only
		http.authorizeRequests().antMatchers("/product").access("hasROLE('ROLE_MANAGER')");
		
		
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		
		http.authorizeRequests().and().formLogin()
		//submit url form login
		.loginProcessingUrl("/j_spring_scurity_check") //submit url
		.loginPage("/login")
		.defaultSuccessUrl("/accountInfo")
		.failureUrl("/login?error=true")
		.usernameParameter("username")
		.passwordParameter("password")
		.and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
		
	}
}
