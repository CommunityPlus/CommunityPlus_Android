package com.example.hackathon.models;

import java.util.ArrayList;
import java.util.Arrays;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

//{"name":"Hackathon","description":"This is going to be a long weeked","time":"2000-01-01T17:51:00Z","date":"2013-07-19","location":"Wits","attendees":[{"name":"Matthew Looi","user_id":1,"skill":"Parkour"},{"name":"Minnie Carroll","user_id":2,"skill":"Issac Gaylord"},{"name":"Giovanni Wilkinson","user_id":3,"skill":"Devyn McKenzie"},{"name":"Hyman Stamm","user_id":4,"skill":"Jevon Sipes"},{"name":"Lucio Wilkinson","user_id":5,"skill":"Jonas Spencer"},{"name":"Florian Stamm","user_id":6,"skill":"Reva Ortiz"},{"name":"Amari Kassulke","user_id":7,"skill":"Taya Medhurst"},{"name":"Ms. Kathryn Oberbrunner","user_id":8,"skill":"Kira Wilderman"},{"name":"Sasha Walter","user_id":9,"skill":"Rod Tremblay IV"},{"name":"Theo D'Amore","user_id":10,"skill":"Mellie Johnston"},{"name":"Mrs. Ottilie Schaefer","user_id":11,"skill":"Brandy Kautzer"}]}

// want: a single object for a  single event.

public class EventProfile {
	
	@SerializedName("name")
	private String name;
	//public String status;
	@SerializedName("date")
	private String date;
	@SerializedName("description")
	private String description;
	@SerializedName("time")
	private String time;
	@SerializedName("location")
	private String location;
	@SerializedName("photo_url")
	private String eventPicture;
	@SerializedName("attendees")
	public ArrayList<ListUsers> attendees;
	@SerializedName("host_name")
	private String hostName;
	@SerializedName("attendee_id")
	private int attendeeID;
	

	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
//	public int getHostId() {
//		return hostId;
//	}
//	public void setHostId(int hostId) {
//		this.hostId = hostId;
//	}
	public String getName() {
		Log.d("EventProfile", "hello???");
		Log.d("EventProfile", "event profile name: " + name);
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getEventPicture() {
		return eventPicture;
	}
	public void setEventPicture(String eventPicture) {
		this.eventPicture = eventPicture;
	}
	public ArrayList<ListUsers> getAttendees() {
		return attendees;
	}
	public void setAttendees(ArrayList<ListUsers> attendees) {
		this.attendees = attendees;
	}
	public String getDate() {
		Log.d("EventProfile", "date format: " + this.date);
		return this.date;
	}
	
	public void setDate(String _date) {
		this.date = _date;
	}
	
	public String getTime() {
		Log.d("EventProfile", "time format: " + this.time);
		return this.time;
	}
	
	public void setTime(String _time) {
		this.time = _time;
	}
	
	public int getAttendeeId(){
		return this.attendeeID;
	}

	
}
