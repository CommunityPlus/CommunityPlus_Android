package com.example.hackathon.models;

import com.google.gson.annotations.SerializedName;

import android.graphics.Bitmap;
import android.util.Log;

public class ListEvents {
	
	@SerializedName("id")
	int eventId;
	@SerializedName("photo_url")
	String eventPicture;
	@SerializedName("name")
	String name;
	@SerializedName("location")
	String location;
	@SerializedName("date")
	String date;
	@SerializedName("time")
	String time;
	
	public ListEvents(int eventId, String eventPicture, String name,
 String location) {
		super();
		this.eventId = eventId;
		this.eventPicture = eventPicture;
		this.name = name;
		this.location = location;
	}

	public ListEvents(){};

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "ListEvents [eventId=" + eventId + ", eventPicture="
				+ eventPicture + ", name=" + name + 
				 ", location=" + location + "]";
	}
	public int getEventId() {
		Log.d("getEventId", Integer.toString(eventId));
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public String getEventPicture() {
		return eventPicture;
	}
	public void setEventPicture(String eventPicture) {
		this.eventPicture = eventPicture;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEventDate(){
		return this.date;
	}
	public String getEventTime(){
		return this.time;
	}
	
	

}
