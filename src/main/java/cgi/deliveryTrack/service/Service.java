package cgi.deliveryTrack.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cgi.deliveryTrack.bean.Action;
import cgi.deliveryTrack.bean.Context;
import cgi.deliveryTrack.bean.Response;
import cgi.deliveryTrack.enumeration.StatusEnum;
import cgi.deliveryTrack.transfert.FileTransfert;


/**
 * Réalisé par Julien GRIFFAULT
 * CGI - France
 */

public class Service {
	
	private FileTransfert delivery = new FileTransfert(); 
	
	List<Action> actionList = new ArrayList<Action>();
	Map<String, String> planDeClassement = new HashMap<String, String>();
	
	public Service() {
		planDeClassement.put("Livraison", "C:\\DeliveryTrack2\\");
		planDeClassement.put("Validation", "AdresseValidation");
		planDeClassement.put("Invalidation", "AdresseInvalidation");
		planDeClassement.put("Archivage", "AdresseArchivage");
		
	}
	
	public String fileTransfert(Context context) {

		// Détermination de la destination en fonction du plan de classement établi
		String destination = getDestination(context);
		
		// Parsing de du fileNewName
		String fileNewName = context.getFile().getName();
		
		// Construction de l'action
		Action action = new Action(context.getProvenance(), context.getFile(), destination, fileNewName);
		
		// Transfert du fichier
		Response response = delivery.transfert(action);
		
		if (response.getStatus() == StatusEnum.SUCCESS) {
			response.setMessage("Fichier Transferé");
		}
		
		return response.getMessage();
	}
	
	
	
	
	
	
	private String getDestination(Context context) {
		return planDeClassement.get(context.getProvenance().toString());
	}

}
