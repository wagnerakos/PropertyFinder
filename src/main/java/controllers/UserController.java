package controllers;

import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Faces;

import entities.User;
import lombok.Getter;
import lombok.Setter;
import service.OperatorBean;

@ManagedBean
@SessionScoped
public class UserController {

	@EJB
	private OperatorBean ob;

	@Getter
	@Setter
	private User user;
	
	@Getter
	@Setter
	private String password;

	@PostConstruct
	public void init() {
		Principal userPrincipal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		user = ob.getUser(userPrincipal.getName());
	}

	public String delete(long id) {
		ob.deleteUser(id);
		return logout();
	}

	public void save() {
		ob.updateUser(user, password);
	}
	
	public String logout() {
		Faces.invalidateSession();
		return FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath() + "/public/login.xhtml?faces-redirect=true";
	}
	
	public String profile() {
		return "/user/profile.xhtml?faces-redirect=true";
	}
}
