package service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dal.UserFacade;
import entities.Role;
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

	public User login(String username, String password) {
		List<User> users = userFacade.findAll();
		User correct = null;
		for (User user : users) {
			if (user.getUsername().equals(username))
				if (user.getPasswordHash().equals(password))
					correct = user;
		}

		if (correct == null)
			throw new RuntimeException("Bad user or password");

		return correct;
	}

	public List<User> listUsers() {
		return userFacade.findAll();
	}

	public void deleteUser(long id) {
		User user = userFacade.find(id);
		if (user != null) {
			userFacade.remove(user);
		}
	}
}
