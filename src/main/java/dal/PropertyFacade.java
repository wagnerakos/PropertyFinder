package dal;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Property;

/**
 * Session Bean implementation class UserBean
 */
@Stateless
public class PropertyFacade extends AbstractFacade<Property> {
	
	@PersistenceContext
	EntityManager em;

    /**
     * Default constructor. 
     */
    public PropertyFacade() {
    	super(Property.class);
    }
       
    @Override
	protected EntityManager em() {
		return em;
	}
    
    public List<Property> findAllWithAddress() {
    	TypedQuery<Property> query = em().createQuery("SELECT p FROM Property p LEFT JOIN FETCH p.address", Property.class);
    	return query.getResultList();
    }
    
    public Property getWithDataById(Long id) {
    	TypedQuery<Property> query = em().createQuery("SELECT p FROM Property p LEFT JOIN FETCH p.address LEFT JOIN FETCH p.user LEFT JOIN FETCH p.images WHERE p.id = :id", Property.class);
    	query.setParameter("id", id);
    	return query.getSingleResult();
    }
}
