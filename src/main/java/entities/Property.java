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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
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
	
	@OneToOne
    @PrimaryKeyJoinColumn
	private Address address;
	
	@Column(name = "yearofbuild")
	private Integer yearOfBuild;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "uploadtime")
	private Date uploadTime;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid", nullable = false)
	private User user;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "property")
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
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
}
