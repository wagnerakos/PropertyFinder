package dal;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
