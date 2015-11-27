package controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import dal.PropertyFacade;
import entities.Property;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@RequestScoped
public class PropertyDetailsController {
	
	@EJB
	private PropertyFacade propertyFacade;
	
	@Getter
	@Setter
	@ManagedProperty(value = "#{param.id}")
	private String propertyId;
	
	@Getter
	@Setter
	private Property property;
	
	@PostConstruct
	public void init() {
		property = propertyFacade.getWithDataById(Long.valueOf(propertyId));
	}
}
