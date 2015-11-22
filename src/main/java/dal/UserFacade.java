package dal;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.User;

/**
 * Session Bean implementation class UserBean
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {
	
	@PersistenceContext
	EntityManager em;

    /**
     * Default constructor. 
     */
    public UserFacade() {
    	super(User.class);
    }
       
    @Override
	protected EntityManager em() {
		return em;
	}
}
