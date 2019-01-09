package cgi.deliveryTrack.bean;

import cgi.deliveryTrack.enumeration.ApiEnum;

public class CancelAction {
	
	private ApiEnum provenance;
	private String message;
	
	public CancelAction(String message, ApiEnum provenance) {
		this.provenance = provenance;
		this.message = message;
	}
	
	
	public ApiEnum getProvenance() {
		return provenance;
	}
	public void setProvenance(ApiEnum provenance) {
		this.provenance = provenance;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
