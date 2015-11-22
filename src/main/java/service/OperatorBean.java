package service;

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
}
