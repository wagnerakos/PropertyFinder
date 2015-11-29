package dal;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
    
    public User getUserByUsername(String username) {
    	String queryString = "SELECT u FROM User u WHERE u.username = :username";
    	
    	TypedQuery<User> query = em().createQuery(queryString, User.class);
    	query.setParameter("username", username);
    	
    	return query.getSingleResult();
    }
    
    public boolean isUsernameReserved(String username) {
    	String queryString = "SELECT COUNT(u) FROM User u WHERE u.username = :username";
    	
    	TypedQuery<Long> query = em().createQuery(queryString, Long.class);
    	query.setParameter("username", username);
    	
    	return query.getSingleResult() > 0;
    }
}
