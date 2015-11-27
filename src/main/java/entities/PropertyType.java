package entities;

import lombok.Getter;
import lombok.Setter;

public enum PropertyType {
	BRICK("T�gla �p�t�s�"),
	PANEL("Panel"),
	FAMILY_HOUSE("Csal�di h�z"),
	SEMIDETACHED_HOUSE("Ikerh�z"),
	APARTMENT_HOUSE("T�rsash�z");
	
	@Getter
	@Setter
	private String label;
	
	private PropertyType(String label) {
		this.label = label;
	}
}
