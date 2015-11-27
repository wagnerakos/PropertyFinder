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
	
	@Getter
	@Setter
	private Integer first = 0;
	
	@Getter
	@Setter
	private Integer pageSize = 5;
	
	@Getter
	@Setter
	private Integer totalCount;
	
	@EJB
	private PropertyFacade propertyFacade;
	
	@Setter
	private List<Property> properties;

	@PostConstruct
	public void init() {
		totalCount = propertyFacade.count();
	}
	
	public static String getAddressAsString(Address address) {
		return address.getZipCode() + " " + address.getCity() + ", " + 
					address.getStreet() + " " + address.getAddressNumber();
	}
	
	public void nextPage() {
		first += pageSize;
	}
	
	public void prevPage() {
		first -= pageSize;
	}

	public List<Property> getProperties() {
		return propertyFacade.findAllWithAddress(first, pageSize);
	}
	
	public int getUpperBoundary() {
		return Math.min(first + pageSize, totalCount);
	}
}
