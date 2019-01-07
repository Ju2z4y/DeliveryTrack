package cgi.deliveryTrack.bean;

import cgi.deliveryTrack.enumeration.StatusEnum;

public class Response {
	
	private StatusEnum status;
	private String message;
	
	
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	
}
