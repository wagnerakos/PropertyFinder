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

	public User login(String username, String password) {

		User loggedInUser = getUser(username);

		if (loggedInUser == null || !loggedInUser.getPasswordHash().equals(password))
			throw new RuntimeException("Bad username or password");

		return loggedInUser;
	}

	public User getUser(String userName) {
		List<User> users = userFacade.findAll();
		User user = null;
		for (User u : users) {
			if (u.getUsername().equals(userName))
				user = u;
		}
		return user;
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

	public void updateUser(User user, String newPassword) {
		User find = userFacade.find(user.getId());
		find.setUsername(user.getUsername());
		find.setEmail(user.getEmail());
		if (!newPassword.equals("")) {
			find.setPasswordHash(newPassword);
		}
	}
}
