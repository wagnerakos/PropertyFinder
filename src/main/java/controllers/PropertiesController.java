package controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dal.PropertyFacade;
import entities.Address;
import entities.Property;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@ViewScoped
public class PropertiesController {
	
	@EJB
	private PropertyFacade propertyFacade;
	
	@Getter
	@Setter
	private List<Property> properties;

	@PostConstruct
	public void init() {
		properties = propertyFacade.findAllWithAddress();
	}
	
	public static String getAddressAsString(Address address) {
		return address.getZipCode() + " " + address.getCity() + ", " + 
					address.getStreet() + " " + address.getAddressNumber();
	}
}
