package entities;

public enum PropertyType {
	BRICK("Tégla építésû"),
	PANEL("Panel"),
	FAMILY_HOUSE("Családi ház"),
	SEMIDETACHED_HOUSE("Ikerház"),
	APARTMENT_HOUSE("Társasház");
	
	public String label;
	
	private PropertyType(String label) {
		this.label = label;
	}
}
