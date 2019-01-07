package cgi.deliveryTrack.bean;

import java.io.File;

import cgi.deliveryTrack.enumeration.ApiEnum;

public class Context {

	private File file;
	private ApiEnum provenance;
	
	public Context(ApiEnum provenance, File file) {
		this.file = file;
		this.provenance = provenance;
	}
	
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public ApiEnum getProvenance() {
		return provenance;
	}
	public void setProvenance(ApiEnum provenance) {
		this.provenance = provenance;
	}


}
