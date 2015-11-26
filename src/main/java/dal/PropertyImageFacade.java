package dal;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.PropertyImage;

@Stateless
public class PropertyImageFacade extends AbstractFacade<PropertyImage> {

	@PersistenceContext
	EntityManager em;

    public PropertyImageFacade() {
    	super(PropertyImage.class);
    }
       
    @Override
	protected EntityManager em() {
		return em;
	}
}
