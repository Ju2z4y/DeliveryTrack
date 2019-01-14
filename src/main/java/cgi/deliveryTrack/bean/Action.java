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
	private String fileVersionOrigin;
	private String fileVersionDest; 
	private String fileNewName;
	
	public Action() {
		
	}
	
	public Action (ApiEnum provenance, File file, String destination, String fileNewName) {
		this.setAction(provenance);
		this.setAuteur(System.getProperty("user.name"));
		this.setDate(new Date());
		this.setFileName(file.getName());
		this.setPathOrigin(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("\\")+1));
		this.setPathDest(destination);
		this.setFileNewName(fileNewName);
	}
	
	public Action (Context context, String destination) {
		this.setAction(context.getProvenance());
		this.setAuteur(System.getProperty("user.name"));
		this.setDate(new Date());
		this.setPathOrigin(context.getFile().getAbsolutePath().substring(0, context.getFile().getAbsolutePath().lastIndexOf("\\")+1));
		this.setPathDest(destination);
		parseFileName(context.getFile().getName());
	}
	
	@Override
	public String toString() {
		String reponse = "Action : " + getAction().toString() + "\n";
		reponse += "Acteur : " + getAuteur() + "\n";
		reponse += "Path Origine : " + getPathOrigin() + "\n";
		reponse += "Fichier : " + getFileName() + "\n";
		reponse += "Version Origine : " + getFileVersionOrigin() + "\n";
		reponse += "Destination : " + getPathDest() + "\n";
//		reponse += "Nouveau nom : " + getFileNewName() + "\n";
		reponse += "-----------------------\n"; 
		return reponse;
	}
	
	public String message() {
		String reponse = "Action : " + getAction().toString() + "\n";
		reponse += "Fichier : " + getFileName() + "\n";
		reponse += "Origine : " + getPathOrigin() + "\n";
		reponse += "Destination : " + getPathDest() + "\n";
		reponse += "Nouveau nom : " + getFileNewName() + "\n";
		reponse += "-----------------------\n"; 
		return reponse;
	}
	
	public String messageTab() {
		String reponse = "->Action : " + getAction().toString() + "\n";
		reponse += "->Fichier : " + getFileName() + "\n";
		reponse += "->Origine : " + getPathOrigin() + "\n";
		reponse += "->Destination : " + getPathDest() + "\n";
		reponse += "->Nouveau nom : " + getFileNewName() + "\n";
		reponse += "-----------------------\n"; 
		return reponse;
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

	public String getFileVersionOrigin() {
		return fileVersionOrigin;
	}

	public void setFileVersionOrigin(String fileVersionOrigin) {
		this.fileVersionOrigin = fileVersionOrigin;
	}

	public String getFileVersionDest() {
		return fileVersionDest;
	}

	public void setFileVersionDest(String fileVersionDest) {
		this.fileVersionDest = fileVersionDest;
	}

	public String deleteMessage() {
		String reponse = "Annulation de l'action\n";
		reponse += this.messageTab();
		return reponse;
	}
	
	
	
	public void parseFileName(String fileName) {
		String fileNameNoVersion = "";
		String fileNameNoExt = fileName.substring(0, fileName.lastIndexOf('.'));
		String fileNameExt = fileName.substring(fileNameNoExt.length());
		boolean isVersion = false;
		int version = 1;
		int version2 = 0;
		
		for (int i = 0; i<fileName.length(); i++) {
			if ((fileName.charAt(i) == 'v') || (fileName.charAt(i) == 'V')) {
				if (Character.isDigit(fileName.charAt(i+1))) {
					isVersion = true;
					version = Integer.parseInt(fileName.charAt(i+1)+"");
					if (fileName.charAt(i+2) == '.') {
						if (Character.isDigit(fileName.charAt(i+3))) {
							version2 = Integer.parseInt(fileName.charAt(i+3)+"");
							if (fileName.charAt(i+4) == '.') {
								if (Character.isDigit(fileName.charAt(i+5))) {
									
								}
							}
						}
					}
				}
			}
		}
		if (!isVersion) {
			fileNameNoVersion = checkSpaces(fileNameNoExt) + fileNameExt;
		} else {
			fileNameNoVersion = checkSpaces(fileNameNoVersion) + fileNameExt;
		}
		this.setFileName(fileNameNoVersion);
		this.setFileVersionOrigin(version + "." + version2);
	}
	
	
	// Méthodes utilitaires
	
	private String checkSpaces(String fileName) {
		while ((fileName.charAt(fileName.length()-1) == ' ') || (fileName.charAt(fileName.length()-1) == '_')){
			fileName = fileName.substring(0, fileName.length()-1);
		}
		return fileName;
	}

}
