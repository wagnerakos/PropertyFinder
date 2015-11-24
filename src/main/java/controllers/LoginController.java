package controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Faces;

import lombok.Getter;
import lombok.Setter;

@ManagedBean
@SessionScoped
public class LoginController {
	
	@Getter
	@Setter
	private String username;
	
	@Getter
	@Setter
	private String password;	
	
	public String login() {
		try {
			Faces.login(username, password);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "/public/properties.xhtml?faces-redirect=true";
	}
	
	public void logout() {
        Faces.invalidateSession();
    }
}
