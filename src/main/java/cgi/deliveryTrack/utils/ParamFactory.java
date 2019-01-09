package cgi.deliveryTrack.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ParamFactory {

	public void saveParam(Map<String, String> paramsMap) {

		try {
			Writer writer = new FileWriter("C:\\DeliveryTrack\\params\\params.json");
		    Gson gson = new GsonBuilder().create();
		    gson.toJson(paramsMap, writer);
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Map<String, String> loadParam(boolean error) {
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		
		try {
			Reader reader = new FileReader("C:\\DeliveryTrack\\params\\params.json");
			Map<String, String> map = gson.fromJson(reader, type);
			System.out.println(map.toString());
			reader.close();
			if (error) {
				map.replace("Error", "true");
				saveParam(map);
			} else {
				map.replace("Error", "false");
				saveParam(map);
			}
			
			return map;
		} catch (FileNotFoundException e) {
			createParams();
			return loadParam(true);
		} catch (IOException e) {
			return null;
		} catch (NullPointerException e) {
			createParams();
			return loadParam(true);
		}
	}
	
	public void createParams() {
		File folder1 = new File("C:\\DeliveryTrack");
		folder1.mkdir();
		File folder = new File("C:\\DeliveryTrack\\params");
		folder.mkdir();
		File livrer = new File("C:\\DeliveryTrack\\livrer");
		livrer.mkdir();
		File valider = new File("C:\\DeliveryTrack\\valider");
		valider.mkdir();
		File invalider = new File("C:\\DeliveryTrack\\invalider");
		invalider.mkdir();
		File archiver = new File("C:\\DeliveryTrack\\archiver");
		archiver.mkdir();
		File logs = new File("C:\\DeliveryTrack\\logs");
		logs.mkdir();
		Map<String, String> params = new HashMap<String, String>();
		params.put("Livraison", "C:\\DeliveryTrack\\livrer\\");
		params.put("Validation", "C:\\DeliveryTrack\\valider\\");
		params.put("Invalidation", "C:\\DeliveryTrack\\invalider\\");
		params.put("Archivage", "C:\\DeliveryTrack\\archiver\\");
		params.put("Logs", "C:\\DeliveryTrack\\logs\\");
		params.put("Error", "true");
		
		try {
			Writer writer = new FileWriter("C:\\DeliveryTrack\\params\\params.json");
		    Gson gson = new GsonBuilder().create();
		    gson.toJson(params, writer);
		    writer.close();
		} catch (IOException e) {
			
		}
	}
	
	public String loadLogsParam() {
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		
		try {
			Reader reader = new FileReader("C:\\DeliveryTrack\\params\\params.json");
			Map<String, String> map = gson.fromJson(reader, type);
			System.out.println(map.toString());
			reader.close();
			
			return map.get("Logs");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (IOException e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public String checkPath(String path) {
		if (path.charAt(path.length()-1) == '\\') {
			return path;
		} else {
			return path + "\\";
		}
	}
}
