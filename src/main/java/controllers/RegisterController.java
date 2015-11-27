package controllers;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import entities.Role;
import entities.User;
import lombok.Getter;
import lombok.Setter;
import service.OperatorBean;

@ManagedBean
@SessionScoped
public class RegisterController {
	
	@Getter
	@Setter
	private String username;
	
	@Getter
	@Setter
	private String password;
	
	@Getter
	@Setter
	private String email;
	
	@EJB
	private OperatorBean ob;
	
	public String register() {
		
		User user = new User();
		user.setUsername(username);
		user.setPasswordHash(password);
		user.setEmail(email);
		user.setRole(Role.USER);
		ob.registerUser(user);
		return "/public/login.xhtml?faces-redirect=true";
	}
}
