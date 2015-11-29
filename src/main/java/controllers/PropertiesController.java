package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import dal.PropertyFacade;
import entities.Address;
import entities.Property;
import entities.QueryParameterHolder;
import entities.User;
import lombok.Getter;
import lombok.Setter;
import util.UserHelper;

@ManagedBean
@ViewScoped
public class PropertiesController {
	
	@Getter
	@Setter
	@ManagedProperty(value = "#{userHelper}")
	private UserHelper userHelper;
	
	@Getter
	@Setter
	private Integer first = 0;
	
	@Getter
	@Setter
	private Integer pageSize = 10;
	
	@Getter
	@Setter
	private Integer totalCount;
	
	@EJB
	private PropertyFacade propertyFacade;
	
	@Setter
	private List<Property> properties;
	
	@Setter
	private List<Property> usersProperties;
	
	@Getter
	@Setter
	private QueryParameterHolder selectedType;
	
	@Getter
	@Setter
	private QueryParameterHolder sizeFrom;
	
	@Getter
	@Setter
	private QueryParameterHolder sizeTo;
	
	@Getter
	@Setter
	private QueryParameterHolder addressZipCode;
	
	@Getter
	@Setter
	private QueryParameterHolder addressCity;
	
	@Getter
	@Setter
	private QueryParameterHolder addressStreet;
	
	@Getter
	@Setter
	private QueryParameterHolder addressAddressNumber;
	
	@Getter
	@Setter
	private QueryParameterHolder priceFrom;
	
	@Getter
	@Setter
	private QueryParameterHolder priceTo;
	
	@Getter
	@Setter
	private String sortField = "price";
	
	@Getter
	@Setter
	private String sortDirection = "ASC";
	
	@Getter
	@Setter
	private Map<String, String> sortFields = new LinkedHashMap<>();
	
	@Getter
	@Setter
	private Map<String, String> sortDirections = new LinkedHashMap<>();
	
	private User currentUser;

	@PostConstruct
	public void init() {
		currentUser = userHelper.getLoggedInUser();
		selectedType = new QueryParameterHolder("type", "T�pus", "=", null);
		sizeFrom = new QueryParameterHolder("squareFootage", "M�ret", ">=", null, Double.class, "squareFootageFrom");
		sizeTo = new QueryParameterHolder("squareFootage", "M�ret", "<=", null, Double.class, "squareFootageTo");
		addressZipCode = new QueryParameterHolder("address.zipCode", "Ir�ny�t�sz�m", "LIKE", null, String.class, "zipCode");
		addressCity = new QueryParameterHolder("address.city", "V�ros", "LIKE", null, String.class, "city");
		addressStreet = new QueryParameterHolder("address.street", null, "LIKE", null, String.class, "street");
		addressAddressNumber = new QueryParameterHolder("address.addressNumber", null, "LIKE", null, String.class, "addressNumber");
		priceFrom = new QueryParameterHolder("price", "�r", ">=", null, Integer.class, "priceFrom");
		priceTo = new QueryParameterHolder("price", "�r", "<=", null, Integer.class, "priceTo");
		
		for (QueryParameterHolder param : getQueryParameters()) {
			if (param.getLabel() != null) {
				sortFields.put(param.getLabel(), param.getFieldName());
			}
		}
		
		sortDirections.put("N�vekv�", "ASC");
		sortDirections.put("Cs�kken�", "DESC");
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
		return propertyFacade.findAllWithAddressWithFilters(
				first, 
				pageSize, 
				getQueryParameters(),
				sortField,
				sortDirection,
				true);
	}
	
	public List<Property> getUsersProperties() {
		List<QueryParameterHolder> params = new ArrayList<>(getQueryParameters());
		params.add(new QueryParameterHolder("user.id", null, "=", String.valueOf(currentUser.getId()), Long.class, "userid"));
		return propertyFacade.findAllWithAddressWithFilters(
				first, 
				pageSize, 
				params,
				sortField,
				sortDirection,
				false);
	}
	
	public List<QueryParameterHolder> getQueryParameters() {
		return Arrays.asList(
				priceFrom,
				priceTo,
				selectedType,
				sizeFrom,
				sizeTo,
				addressZipCode,
				addressCity,
				addressStreet,
				addressAddressNumber				
		);
	}
	
	public int getUpperBoundary() {
		return Math.min(first + pageSize, totalCount);
	}
	
	public int getLowerBoundary() {
		return Math.min(first + 1, totalCount);
	}
	
	public void applyFilters() {
		first = 0;
		totalCount = propertyFacade.getCountWithFilters(getQueryParameters(), true);
	}
	
	public void applyFiltersOnUsersProperties() {
		first = 0;
		List<QueryParameterHolder> params = new ArrayList<>(getQueryParameters());
		params.add(new QueryParameterHolder("user.id", null, "=", String.valueOf(currentUser.getId()), Long.class, "userid"));
		totalCount = propertyFacade.getCountWithFilters(params, false);
	}	
	
	public void onLoad(boolean onlyUsersProperties) {
		if (onlyUsersProperties) {
			List<QueryParameterHolder> params = new ArrayList<>(getQueryParameters());
			params.add(new QueryParameterHolder("user.id", null, "=", String.valueOf(currentUser.getId()), Long.class, "userid"));
			totalCount = propertyFacade.getCountWithFilters(params, false);
		} else {
			totalCount = propertyFacade.getCountWithFilters(getQueryParameters(), true);
		}		
	}
}
