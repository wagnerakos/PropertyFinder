package service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dal.UserFacade;
import entities.User;

/**
 * Session Bean implementation class OperatorBean
 */
@Stateless
public class OperatorBean {
	
	@EJB
	private UserFacade userFacade;

	public void registerUser(User user) {
		userFacade.create(user);
	}

	public void login(String username, String password) {
		List<User> users = userFacade.findAll();
		boolean correct = false;
		for (User user : users) {
			if (user.getUsername().equals(username))
				if (user.getPasswordHash().equals(password))
					correct = true;
		}
		
		if (!correct)
			throw new RuntimeException("Bad user or password");
	}
}
