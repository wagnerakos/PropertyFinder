package entities;

import lombok.Getter;
import lombok.Setter;

public enum PropertyType {
	BRICK("Tégla építésû"),
	PANEL("Panel"),
	FAMILY_HOUSE("Családi ház"),
	SEMIDETACHED_HOUSE("Ikerház"),
	APARTMENT_HOUSE("Társasház");
	
	@Getter
	@Setter
	private String label;
	
	private PropertyType(String label) {
		this.label = label;
	}
}
