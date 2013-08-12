package com.example.hackathon.models;

import com.google.gson.annotations.SerializedName;

public class Attendees {
	
	@SerializedName("event_id")
	private int event_id;
	@SerializedName("user_id")
	private int user_id;
	@SerializedName("skill_id")
	private int skill_id;
	
	
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
	public int getSkill_id() {
		return skill_id;
	}
	public void setSkill_id(int skill_id) {
		this.skill_id = skill_id;
	}
	
	
	
	
}
