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
		User user = ob.login(username, password);

		try {
			Faces.login(username, password);
		} catch (ServletException e) {
			e.printStackTrace();
		}

		switch (user.getRole()) {
		case ADMIN:
			return "/admin/users.xhtml?faces-redirect=true";
		case USER:
			return "/user/profile.xhtml?faces-redirect=true";
		default:
			return "/user/profile.xhtml?faces-redirect=true";
		}
	}

	public String logout() {
		Faces.invalidateSession();
		return "/public/properties.xhtml?faces-redirect=true";
	}
}
