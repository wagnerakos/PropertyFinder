package controllers;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dal.PropertyFacade;
import entities.Property;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@ViewScoped
public class PropertyDetailsController {
	
	@EJB
	private PropertyFacade propertyFacade;
	
	@Getter
	@Setter
	private String propertyId;
	
	@Setter
	private Property property = null;
	
	public void inactivate() {
		property.setActive(false);
		propertyFacade.edit(property);
	}
	
	public Property getProperty() {
		if (property == null) {
			property = propertyFacade.getWithDataById(Long.valueOf(propertyId));
		}
		return property;
	}
	
	public void onLoad() {
		getProperty();
		property.setNumberOfViews(property.getNumberOfViews() + 1);
		propertyFacade.edit(property);
	}
}
