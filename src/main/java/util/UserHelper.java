package util;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dal.UserFacade;
import entities.User;

@ManagedBean
@SessionScoped
public class UserHelper {
	
	@EJB
	private UserFacade userFacade;
	
	public boolean isUserInRole(String role) {
		return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role);
	}
	
	public User getLoggedInUser() {
		String currentUsername = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
		if (currentUsername != null) {
			return userFacade.getUserByUsername(currentUsername);
		}
		return null;
	}

}
