package com.example.museumtour.models;


public class DisplayModel {
	private String mID;
	private String mName;

	private String mPictureURL;
	private String mDescription;
	
	public DisplayModel(String id, String name, String pictureURL, String description){
		this.mID = id;
		this.mName = name;
		this.mPictureURL = pictureURL;
		this.mDescription = description;
	}
	
	public String getName() {
		return this.mName;
	}
	
	public void setName(String name) {
		this.mName = name;
	}

	
	public String getID() {
		return this.mID;
	}
	public void setID(String iD) {
		this.mID = iD;
	}
	public String getPictureURL() {
		return this.mPictureURL;
	}
	public void setPictureURL(String pictureURL) {
		this.mPictureURL = pictureURL;
	}
	public String getDescription() {
		return this.mDescription;
	}

	public void setDescription(String description) {
		this.mDescription = description;
	}
}
