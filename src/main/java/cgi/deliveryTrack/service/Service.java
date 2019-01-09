package cgi.deliveryTrack.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cgi.deliveryTrack.bean.Action;
import cgi.deliveryTrack.bean.CancelAction;
import cgi.deliveryTrack.bean.Context;
import cgi.deliveryTrack.bean.Response;
import cgi.deliveryTrack.enumeration.ApiEnum;
import cgi.deliveryTrack.enumeration.StatusEnum;
import cgi.deliveryTrack.logs.LogFactory;
import cgi.deliveryTrack.transfert.FileTransfert;
import cgi.deliveryTrack.utils.ParamFactory;
import cgi.deliveryTrack.utils.VersionControl;


/**
 * Réalisé par Julien GRIFFAULT
 * CGI - France
 */

public class Service {
	
	private final FileTransfert delivery = new FileTransfert(); 
	private final LogFactory logFactory = new LogFactory();
	private final VersionControl versionControl = new VersionControl();
	private final ParamFactory paramFactory = new ParamFactory();
	
	List<Action> actionList = new ArrayList<Action>();
	Map<String, String> planDeClassement = new HashMap<String, String>();
	
	public Service() {
		planDeClassement = paramFactory.loadParam(false);
	}
	
	
	
	// Permet le transfert d'un fichier en fonction d'un contexte.
	public String fileTransfert(Context context) {

		// Détermination de la destination en fonction du plan de classement établi
		String destination = getDestination(context);
		
		// Parsing de la nouvelle version du fichier
		Response response = versionControl.control(context, destination);

		if (response.getStatus() == StatusEnum.SUCCESS) {
			// Construction de l'action (response.getMessage() = fileNewName)
			Action action = new Action(context.getProvenance(), context.getFile(), destination, response.getMessage());
			
			// Transfert du fichier
			response = delivery.transfert(action);
			 
			if (response.getStatus() == StatusEnum.SUCCESS) {
				// Ajout d'une trace
				logFactory.generateTrace(planDeClassement.get("Logs"), action);
				// Ajout de l'action dans le cash
				actionList.add(action);
				// Transmission de l'information sur l'action à l'utilisateur
				response.setMessage(action.message());
			}
		}
		
		return response.getMessage();
	}
	
	public CancelAction cancelLastAction() {
		CancelAction cancelAction;
		
		try {
			// Récupération de la dernière action effectuée
			Action actionToDelete = actionList.get(actionList.size()-1);
			// Génération de la réponse
			cancelAction = new CancelAction(actionToDelete.deleteMessage(), actionToDelete.getAction());
			// Gestion du ROLLBACK
			delivery.cancelDelivery(actionToDelete);
			// Suppression de l'action traitée
			actionList.remove(actionList.size()-1);
		} catch (Exception e) {
			cancelAction = new CancelAction("Aucune action enregistrée", ApiEnum.ERROR);
			return cancelAction;
		}
		return cancelAction;
	}
	
	public boolean getParamLoadingError() {
		boolean error;
		if (planDeClassement.get("Error") == "true") {
			error = true;
		} else {
			error = false;
		}
		return error;
	}
	
	private String getDestination(Context context) {
		return planDeClassement.get(context.getProvenance().toString());
	}



	public void majPdc(ApiEnum provenance, String adresse) {
		adresse = paramFactory.checkPath(adresse);
		planDeClassement.replace(provenance.toString(), adresse);
		paramFactory.saveParam(planDeClassement);
		
	}

}
