package controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dal.PropertyFacade;
import entities.Property;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@SessionScoped
public class PropertiesController {
	
	@EJB
	private PropertyFacade propertyFacade;
	
	@Getter
	@Setter
	private List<Property> properties;

	@PostConstruct
	public void init() {
		properties = propertyFacade.findAll();
	}
}
