package dal;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Address;

@Stateless
public class AddressFacade extends AbstractFacade<Address> {
	
	@PersistenceContext
	EntityManager em;

    public AddressFacade() {
    	super(Address.class);
    }
       
    @Override
	protected EntityManager em() {
		return em;
	}
}
