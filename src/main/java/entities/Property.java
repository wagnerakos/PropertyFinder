package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "property")
public class Property {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private PropertyType type;
	
	@Column(name = "squarefootage")
	private Double squareFootage;
	
	@OneToOne(orphanRemoval=true)
	@JoinColumn(name = "addressid")
	private Address address;
	
	@Column(name = "yearofbuild")
	private Integer yearOfBuild;
	
	@Column(name = "price")
	private Long price;
	
	@Column(name = "uploadtime")
	private Date uploadTime;	
	
	@Column(name = "active")
	private Boolean active;
	
	@Column(name = "numberofviews")
	private Integer numberOfViews;
	
	@Lob
	@Column(name = "description")
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid", nullable = false)
	private User user;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "property", orphanRemoval=true)
	private List<PropertyImage> images = new ArrayList<>();	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PropertyType getType() {
		return type;
	}

	public void setType(PropertyType type) {
		this.type = type;
	}

	public Double getSquareFootage() {
		return squareFootage;
	}

	public void setSquareFootage(Double squareFootage) {
		this.squareFootage = squareFootage;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Integer getYearOfBuild() {
		return yearOfBuild;
	}

	public void setYearOfBuild(Integer yearOfBuild) {
		this.yearOfBuild = yearOfBuild;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<PropertyImage> getImages() {
		return images;
	}

	public void setImages(List<PropertyImage> images) {
		this.images = images;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getNumberOfViews() {
		return numberOfViews;
	}

	public void setNumberOfViews(Integer numberOfViews) {
		this.numberOfViews = numberOfViews;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
