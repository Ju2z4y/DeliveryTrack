package cgi.deliveryTrack.logs;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import cgi.deliveryTrack.bean.Action;

public class LogFactory {
	
	public void generateTrace(String way, Action action) {
	    List<String> lines;
	    Path pathLog = Paths.get(way + "logs.txt");
	    
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyy hh:mm:ss");
        String formattedDate = formatter.format(action.getDate());
    	lines = Arrays.asList(
    			"Action : " + action.getAction().toString(),
    			"Date : " + formattedDate, 
    			"Acteur : " + System.getProperty("user.name"), 
    			"Fichier : " + action.getFileName(), 
    			"Origine : " + action.getPathOrigin(), 
    			"Destination : " + action.getPathDest(),
    			"Nouveau nom : " + action.getFileNewName(),
    			"------------------------" );
    	
		try {
			Files.write(pathLog, lines, Charset.forName("UTF-8"),
		            StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
