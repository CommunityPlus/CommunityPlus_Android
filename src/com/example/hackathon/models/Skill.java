package com.example.hackathon.models;

import com.google.gson.annotations.SerializedName;


public class Skill
{
	@SerializedName("skill_id")
	public int skillId;
	@SerializedName("name")
	public String name;
	//public String description;
	@SerializedName("numb_events")
	public int numberOfEvents;
	
	public int getSkillId() {
		return skillId;
	}
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumberOfEvents() {
		return numberOfEvents;
	}
	public void setNumberOfEvents(int numberOfEvents) {
		this.numberOfEvents = numberOfEvents;
	}
	
	

	
	// Future:
	// rating?
	// level of proficiency
	
	
	
}