package cgi.deliveryTrack.transfert;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cgi.deliveryTrack.bean.Action;
import cgi.deliveryTrack.bean.Context;
import cgi.deliveryTrack.bean.Response;
import cgi.deliveryTrack.enumeration.ApiEnum;
import cgi.deliveryTrack.enumeration.StatusEnum;

public class FileTransfert {
	
	private BufferedInputStream bis;
	private BufferedOutputStream bos;
	
    private byte[] buf = new byte[10240];
    private int longueur = 0;
    
    public FileTransfert() {
    	
    }
    
	public Response transfert(Action action) {
		Response response = new Response();
    	File file = new File(action.getPathOrigin() + action.getFileName());
    	
	    try {
			bis = new BufferedInputStream(new FileInputStream(file));
		    bos = new BufferedOutputStream(new FileOutputStream(new File(action.getPathDest() + action.getFileNewName())));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setStatus(StatusEnum.ERROR);
			response.setMessage("Fichier non trouvé\n--------------------\n");
			return response;
		}

        try {
    	    while((longueur = bis.read(buf)) > 0){
    	    	bos.write(buf, 0, longueur);
    	    }
			bis.close();
            bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setStatus(StatusEnum.ERROR);
			response.setMessage("Impossible de copier le fichier\n--------------------\n");
			return response;
		}
        
        if ((action.getAction() == ApiEnum.ARCHIVAGE) || 
        		(action.getAction() == ApiEnum.VALIDATION) || 
        		(action.getAction() == ApiEnum.INVALIDATION)) 
        {
            try {
                file.delete();
            } catch (Exception e) {
    			response.setStatus(StatusEnum.ERROR);
    			response.setMessage("Impossible de supprimer le fichier\n--------------------\n");
    			return response;
            }
        }

		response.setStatus(StatusEnum.SUCCESS);
		return response;
	}
	
    
	public void cancelDelivery(Action actionToDelete) {
		File fileDest = new File(actionToDelete.getPathDest() + actionToDelete.getFileNewName());
		File fileOrigin = new File(actionToDelete.getPathOrigin() + actionToDelete.getFileName());
		
	    try {
			bis = new BufferedInputStream(new FileInputStream(fileDest));
		    bos = new BufferedOutputStream(new FileOutputStream(fileOrigin));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        try {
    	    while((longueur = bis.read(buf)) > 0){
    	    	bos.write(buf, 0, longueur);
    	    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
        try {
			bis.close();
            bos.close();
            fileDest.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
