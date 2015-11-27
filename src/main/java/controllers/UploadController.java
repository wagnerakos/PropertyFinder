package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import dal.AddressFacade;
import dal.PropertyFacade;
import dal.PropertyImageFacade;
import entities.Address;
import entities.Property;
import entities.PropertyImage;
import entities.PropertyType;
import lombok.Getter;
import lombok.Setter;
import util.UserHelper;

@ManagedBean
@ViewScoped
public class UploadController {
	
	@Getter
	@Setter
	@ManagedProperty(value = "#{userHelper}")
	private UserHelper userHelper;
	
	@EJB
	private PropertyFacade propertyFacade;
	
	@EJB
	private PropertyImageFacade propertyImageFacade;
	
	@EJB
	private AddressFacade addressFacade;
	
	@Getter
	@Setter
	private String selectedType;
	
	@Getter
	@Setter
	private Map<String, String> propertyTypes;
	
	@Getter
	@Setter
	private String size;
	
	@Getter
	@Setter
	private String addressZipCode;
	
	@Getter
	@Setter
	private String addressCity;
	
	@Getter
	@Setter
	private String addressStreet;
	
	@Getter
	@Setter
	private String addressAddressNumber;
	
	@Getter
	@Setter
	private String yearOfBuild;
	
	@Getter
	@Setter
	private String price;
	
	@Getter
	@Setter
	private Part imageFile;
	
	private List<ImageHolder> uploadedFiles = new ArrayList<>();
	
	@PostConstruct
	public void init() {
		propertyTypes = new HashMap<>();
		for (PropertyType type : PropertyType.values()) {
			propertyTypes.put(type.getLabel(), type.name());
		}
	}
	
	public void upload() {		
		Address address = new Address();
		address.setZipCode(addressZipCode);
		address.setCity(addressCity);
		address.setStreet(addressStreet);
		address.setAddressNumber(addressAddressNumber);
		addressFacade.create(address);
		
		Property property = new Property();
		property.setType(PropertyType.valueOf(PropertyType.class, selectedType));
		property.setSquareFootage(Double.valueOf(size));
		property.setAddress(address);
		property.setYearOfBuild(Integer.valueOf(yearOfBuild));
		property.setPrice(Long.valueOf(price));
		property.setUser(userHelper.getLoggedInUser());
		property.setUploadTime(new Date());
		
		propertyFacade.create(property);
		
		for (ImageHolder imageHolder : uploadedFiles) {
			PropertyImage image = new PropertyImage();
			image.setProperty(property);
			image.setData(imageHolder.data);
			propertyImageFacade.create(image);
		}
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mentés sikeres!"));
	}
	
	public void uploadImage() {
		try (InputStream input = imageFile.getInputStream()) {
			byte[] image = IOUtils.toByteArray(input);
			String filename = getFilename(imageFile);
			uploadedFiles.add(new ImageHolder(filename, image));
			imageFile = null;
			System.out.println(filename);
	    }
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public List<String> getUploadedFilenames() {
		return uploadedFiles.stream().map(f -> f.filename).collect(Collectors.toList());
	}
	
	private String getFilename(Part part) {  
        for (String cd : part.getHeader("content-disposition").split(";")) {  
            if (cd.trim().startsWith("filename")) {  
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");  
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.  
            }  
        }  
        return null;  
    }
	
	private class ImageHolder {
		String filename;
		byte[] data;
		
		ImageHolder(String filename, byte[] data) {
			this.filename = filename;
			this.data = data;
		}
	}
}
