package controllers;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dal.UserFacade;
import entities.Role;
import entities.User;
import lombok.Getter;
import lombok.Setter;
import service.OperatorBean;

@ManagedBean
@ViewScoped
public class RegisterController {
	
	@Getter
	@Setter
	private String username;
	
	@Getter
	@Setter
	private String password;
	
	@Getter
	@Setter
	private String confirmPassword;
	
	@Getter
	@Setter
	private String email;
	
	@EJB
	private OperatorBean ob;
	
	@EJB
	private UserFacade userFacade;
	
	public String register() {
		if (userFacade.isUsernameReserved(username)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("A felhasználónév foglalt, kérem válasszon másikat!"));
			return null;
		}
		
		if (!password.equals(confirmPassword)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("A jelszó és annak megerõsítése nem egyezik!"));
			return null;
		}
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setRole(Role.USER);
		ob.registerUser(user);
		return "/public/login.xhtml?faces-redirect=true";
	}
}
