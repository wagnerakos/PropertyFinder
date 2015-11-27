package dal;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
    
    public byte[] getFirstByPropertyId(Long propertyId) {
    	TypedQuery<PropertyImage> query = 
    			em().createQuery("SELECT pi FROM PropertyImage pi JOIN pi.property p WHERE p.id = :propertyId", PropertyImage.class);
    	query.setParameter("propertyId", propertyId);
    	
    	List<PropertyImage> res = query.getResultList();

    	if (res.isEmpty()) {
    		return new byte[0];
    	}
    	
    	return res.get(0).getData();
    }
}
