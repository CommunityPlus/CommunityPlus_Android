package com.example.hackathon.models;

import com.google.gson.annotations.SerializedName;

public class Attendees {
	
	@SerializedName("event_id")
	private int event_id;
	@SerializedName("user_id")
	private int user_id;
	@SerializedName("teach")
	private boolean is_teach;
	@SerializedName("attendee_id")
	private int attendee_id;
	
	
	public int getEvent_id() {
		return event_id;
	}
	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public Boolean getIs_teach() {
		return is_teach;
	}
	public void setIs_Teach(Boolean _is_teach) {
		this.is_teach= _is_teach;
	}
	public int getAttendee_id(){
		return attendee_id;
	}
	
	
	
	
}
