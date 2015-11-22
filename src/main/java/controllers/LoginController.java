package controllers;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Faces;

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
			ob.login(username, password);
			Faces.login(username, password);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "/user/profile.xhtml?faces-redirect=true";
	}
	
	public void logout() {
        Faces.invalidateSession();
    }
}
