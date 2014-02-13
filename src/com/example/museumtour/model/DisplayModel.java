package com.example.museumtour.model;

public class DisplayModel {
	private String mID;
	private String mPictureURL;
	
	public DisplayModel(String id, String pictureURL){
		this.mID = id;
		this.mPictureURL = pictureURL;
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
}
