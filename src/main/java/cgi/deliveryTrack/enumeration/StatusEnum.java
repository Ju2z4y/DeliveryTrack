package cgi.deliveryTrack.enumeration;

public enum StatusEnum {
	
	SUCCESS("SUCCESS"),
	ERROR("ERREUR");
	
	private String status = "";
	
	StatusEnum(String status) {
		this.status = status;
	}
	
	public String toString() {
		return status;
	}
}

