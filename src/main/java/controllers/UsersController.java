package controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import entities.User;
import service.OperatorBean;

@ManagedBean
@SessionScoped
public class UsersController {

	@EJB
	private OperatorBean ob;

	public void delete(long id) {
		ob.deleteUser(id);
	}
	
	public List<User> getUsersList() {
		return ob.listUsers();
	}
}
