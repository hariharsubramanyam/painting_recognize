package com.example.museumtour.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class MuseumDisplays {
	Map<String, DisplayModel> displayForID;
	
	public MuseumDisplays(JSONObject json){
		final String JSON_DISPLAY = "displays";
		final String JSON_ID = "id";
		final String JSON_NAME = "name";
		final String JSON_DESCRIPTION = "description";
		final String JSON_URL = "url";
		this.displayForID = new HashMap<String, DisplayModel>();
		try{
			JSONArray jsonDisplays = json.getJSONArray(JSON_DISPLAY);
			JSONObject jsonDisplay = null;
			for(int i = 0; i < jsonDisplays.length(); i++){
				jsonDisplay = jsonDisplays.getJSONObject(i);
				this.displayForID.put(jsonDisplay.getString(JSON_ID), new DisplayModel(jsonDisplay.getString(JSON_ID), jsonDisplay.getString(JSON_NAME), jsonDisplay.getString(JSON_URL), jsonDisplay.getString(JSON_DESCRIPTION)));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public DisplayModel getDisplayModelFromAPIResponse(JSONObject json){
		final String JSON_ID = "id";
		final String JSON_STATUS = "status";
		try{
			int status = (int)json.getDouble(JSON_STATUS);
			if(status == 0){
				String id = json.getJSONArray(JSON_ID).get(0).toString();
				if(this.displayForID.containsKey(id)){
					return this.displayForID.get(id);
				}
			}else{
				return null;
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<DisplayModel> toList(){
		ArrayList<DisplayModel> models = new ArrayList<DisplayModel>();
		for(DisplayModel m : this.displayForID.values())
			models.add(m);
		return models;
	}

}
