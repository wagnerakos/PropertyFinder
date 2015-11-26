package controllers;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.ServletException;

import org.omnifaces.util.Faces;

import entities.User;
import lombok.Getter;
import lombok.Setter;
import service.OperatorBean;

@ManagedBean
@SessionScoped
public class LoginController {

	@Getter
	@Setter
	private String username;

	@Getter
	@Setter
	private String password;

	@EJB
	private OperatorBean ob;

	public String login() {
		try {
			Faces.login(username, password);
		} catch (ServletException e) {
			e.printStackTrace();
		}

		return "/public/properties.xhtml?faces-redirect=true";
	}
	
	public String register() {
		return "/public/register.xhtml?faces-redirect=true";
	}

	public String logout() {
		Faces.invalidateSession();
		return "/public/properties.xhtml?faces-redirect=true";
	}
}
