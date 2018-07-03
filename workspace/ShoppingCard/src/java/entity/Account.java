package java.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Accounts")
public class Account implements Serializable{

	private static final long serialVersionUID=1L;
	
	public static final String ROLE_MANAGER="ROLE";
	public static final String ROLE_EMPLOYEE="EMPLOYEE";
	
	@Id
	@Column(name="username",length=20,nullable=false)
	private String userName;
	@Column(name="password",length=20,nullable=false)
	private String password;
	@Column(name="active",length=1,nullable=false)
	private boolean active;
	@Column(name="userRole",length=20)
	private String userRole;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Acoount [userName=");
		builder.append(userName);
		builder.append(", password=");
		builder.append(password);
		builder.append(", active=");
		builder.append(active);
		builder.append(", userRole=");
		builder.append(userRole);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
