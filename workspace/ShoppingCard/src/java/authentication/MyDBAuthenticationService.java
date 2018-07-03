package java.authentication;

import java.dao.AccountDAO;
import java.entity.Account;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyDBAuthenticationService implements UserDetailsService{

	@Autowired
	private AccountDAO accountDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account=accountDAO.findAccount(username);
		System.out.println("Account:"+account);
		
		if(account==null){
			throw new UsernameNotFoundException("User"+username+"was not fount in database");
		}
		//employee, manager,..
		String role=account.getUserRole();
		
		List<GrantedAuthority> grantList=new ArrayList<GrantedAuthority>();
		
		//role employee,role manager
		GrantedAuthority authority=new SimpleGrantedAuthority("ROLE_"+role);
		grantList.add(authority);
		
		boolean enabled=account.isActive();
		boolean accountNonExpired=true;
		boolean credentialsNonExpired=true;
		boolean accountNonLocked=true;
		
		UserDetails userDetails=(UserDetails)new User(account.getUserName(),
		account.getPassword(),enabled,accountNonExpired,
		credentialsNonExpired,accountNonLocked,grantList);
		
		
		return userDetails;
	}

}
