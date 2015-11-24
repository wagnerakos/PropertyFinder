package util;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class UserHelper {
	
	public boolean isUserInRole(String role) {
		return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role);
	}

}
