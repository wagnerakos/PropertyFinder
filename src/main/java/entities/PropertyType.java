package entities;

public enum PropertyType {
	BRICK("T�gla �p�t�s�"),
	PANEL("Panel"),
	FAMILY_HOUSE("Csal�di h�z"),
	SEMIDETACHED_HOUSE("Ikerh�z"),
	APARTMENT_HOUSE("T�rsash�z");
	
	public String label;
	
	private PropertyType(String label) {
		this.label = label;
	}
}
