package controllers;

import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
	private String oldPassword;
	
	@Getter
	@Setter
	private String newPassword;
	
	@Getter
	@Setter
	private String confirmPassword;
	
	@Getter
	@Setter
	private boolean success;

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
		success = false;
		if (oldPassword != null && !oldPassword.equals("")) {
			if (!oldPassword.equals(user.getPassword())) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Helytelen régi jelszó!"));
				return;			
			}
			if (newPassword == null || newPassword.equals("")) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Helytelen új jelszó!"));
				return;
			}
			if (!newPassword.equals(confirmPassword)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Az új jelszó és annak megerõsítése nem egyezik!"));
				return;
			}
		}
		
		ob.updateUser(user, newPassword);
		
		success = true;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mentés sikeres!"));
	}
	
	public String logout() {
		Faces.invalidateSession();
		return "/public/properties.xhtml?faces-redirect=true";
	}
	
	public String profile() {
		return "/user/profile.xhtml?faces-redirect=true";
	}
}
