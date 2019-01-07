package cgi.deliveryTrack.enumeration;

public enum ApiEnum {

	LIVRAISON("Livraison"),
	VALIDATION("Validation"),
	INVALIDATION("Invalidation"),
	ARCHIVAGE("Archivage");
	
	private String provenance = "";
	
	ApiEnum(String provenance) {
		this.provenance = provenance;
	}
	
	public String toString() {
		return provenance;
	}
}
