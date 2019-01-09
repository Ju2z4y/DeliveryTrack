package cgi.deliveryTrack.utils;

import java.io.File;

import cgi.deliveryTrack.bean.Context;
import cgi.deliveryTrack.bean.Response;
import cgi.deliveryTrack.enumeration.StatusEnum;
import cgi.deliveryTrack.exception.DeliveryTrackException;

public class VersionControl {
	
	public Response control(Context context, String destination) {
		Response response = new Response();
		String fileName = context.getFile().getName();
		String fileNewName = "";
		
		if (context.getFile().isFile()) {
			
			switch(context.getProvenance()) {
			  case LIVRAISON:
			    
					try {
						fileNewName = checkIfVersion(fileName, true);
					} catch (DeliveryTrackException e) {
						// TODO Auto-generated catch block
						response.setMessage(e.getMessage());
						response.setStatus(StatusEnum.ERROR);
						return response;
					}
					
					File checkFile = new File(destination + fileNewName);
					
		        	while (checkFile.exists()) {
		        		fileNewName = versionIncr2(fileNewName);
		        		checkFile = new File(destination + fileNewName);
		        	}

				  break;
			  case ARCHIVAGE:

					try {
						fileNewName = checkIfVersion(fileName, false);
					} catch (DeliveryTrackException e) {
						// TODO Auto-generated catch block
						response.setMessage(e.getMessage());
						response.setStatus(StatusEnum.ERROR);
						return response;
					}
					fileNewName = versionIncr(fileNewName);
				  
				  break;
			  default: 
				// VALIDATION & INVALIDATION
					try {
						fileNewName = checkIfVersion(fileName, false);
					} catch (DeliveryTrackException e) {
						// TODO Auto-generated catch block
						response.setMessage(e.getMessage());
						response.setStatus(StatusEnum.ERROR);
						return response;
					}
			}
			
		} else {
			response.setStatus(StatusEnum.ERROR);
			response.setMessage("Ceci n'est pas un fichier\n-------------------\n");
			return response;
		}	
		
    	response.setStatus(StatusEnum.SUCCESS);
    	response.setMessage(fileNewName);
		
		return response;
	}
	
	private String checkIfVersion(String fileName, boolean addversion) throws DeliveryTrackException {
		boolean isVersion = false;
		String versOrNot = "Le fichier doit être non versionné, ou versionné au format vX.X\n--------------------\n";
		String vers = "Le fichier doit être versionné au format vX.X\n--------------------\n";
		
		for (int i = 0; i<fileName.length(); i++) {
			if ((fileName.charAt(i) == 'v') || (fileName.charAt(i) == 'V')) {
				if (Character.isDigit(fileName.charAt(i+1))) {
					if (fileName.charAt(i+2) == '.') {
						if (Character.isDigit(fileName.charAt(i+3))) {
							isVersion = true;
							if (fileName.charAt(i+4) == '.') {
								if (Character.isDigit(fileName.charAt(i+5))) {
									throw new DeliveryTrackException(addversion?versOrNot:vers);
								}
							}
						} else {
							throw new DeliveryTrackException(addversion?versOrNot:vers);
						}
					} else {
						throw new DeliveryTrackException(addversion?versOrNot:vers);
					}
					
				}
			}
		}

		if (!isVersion && addversion) {
			fileName = addVersion(fileName);
		}
		
		if (!isVersion && !addversion) {
			throw new DeliveryTrackException(vers);
		}
	
		return fileName;
	}
	
	private String addVersion(String fileName) {
		String fileNameNoExt = fileName.substring(0, fileName.lastIndexOf('.'));
		String fileNameExt = fileName.substring(fileNameNoExt.length());
		fileName = fileNameNoExt +"_v1.0" + fileNameExt;
		return fileName;
	}
	
	private String versionIncr(String fileName) {
		
		String fileNameNoVersion;
		String fileNameEnd;
		String fileVersion;
		String fileVersion2;
		int version;
		int version2;
		
		for (int i = 0; i<fileName.length(); i++) {
			if ((fileName.charAt(i) == 'v') || (fileName.charAt(i) == 'V')) {
				if (Character.isDigit(fileName.charAt(i+1))) {
					fileNameNoVersion = fileName.substring(0, i+1);
					fileNameEnd = fileName.substring(fileNameNoVersion.length());
					fileVersion = fileNameEnd.substring(0, fileNameEnd.lastIndexOf('.'));
					fileVersion = fileVersion.substring(0, fileVersion.lastIndexOf('.'));
					fileNameEnd = fileNameEnd.substring(fileVersion.length());
					fileVersion2 = fileNameEnd.substring(1, fileNameEnd.lastIndexOf('.'));
					fileNameEnd = fileNameEnd.substring(fileVersion2.length()+1);
					version2 = Integer.parseInt(fileVersion2);
					version2 = 0;
					version = Integer.parseInt(fileVersion);
					version++;
					fileName = fileNameNoVersion + version + "." + version2 + fileNameEnd;
				}
			}
		}
		
		return fileName;
	}
	
	private String versionIncr2(String fileName) {
		
		String fileNameNoExt = fileName.substring(0, fileName.lastIndexOf('.'));
		String fileNameExt = fileName.substring(fileNameNoExt.length());
		String fileNameNoVersion = fileNameNoExt.substring(0, fileNameNoExt.lastIndexOf('.'));
		String fileNameVersion = fileNameNoExt.substring(fileNameNoVersion.length()+1);
		int version = Integer.parseInt(fileNameVersion);
		version ++;
		fileNameVersion = "." + version;
		fileName = fileNameNoVersion + fileNameVersion + fileNameExt;

		return fileName;
	}

}
