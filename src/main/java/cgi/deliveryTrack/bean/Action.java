package cgi.deliveryTrack.bean;
import java.io.File;
import java.util.Date;

import cgi.deliveryTrack.enumeration.ApiEnum;

public class Action {
	
	private ApiEnum action;
	private Date date;
	private String auteur;
	private String pathOrigin;
	private String pathDest;
	private String fileName;
	private String fileNewName;
	
	public Action (ApiEnum provenance, File file, String destination, String fileNewName) {
		this.setAction(provenance);
		this.setAuteur(System.getProperty("user.name"));
		this.setDate(new Date());
		this.setFileName(file.getName());
		this.setPathOrigin(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("\\")+1));
		this.setPathDest(destination);
		this.setFileNewName(fileNewName);
	}

	public ApiEnum getAction() {
		return action;
	}

	public void setAction(ApiEnum action) {
		this.action = action;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public String getPathOrigin() {
		return pathOrigin;
	}

	public void setPathOrigin(String pathOrigin) {
		this.pathOrigin = pathOrigin;
	}

	public String getPathDest() {
		return pathDest;
	}

	public void setPathDest(String pathDest) {
		this.pathDest = pathDest;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileNewName() {
		return fileNewName;
	}

	public void setFileNewName(String fileNewName) {
		this.fileNewName = fileNewName;
	}
	

}
