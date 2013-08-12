package com.example.hackathon.models;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

//{"name":"Matthew Looi","description":"He loves Simon Sinek","skills":[{"skill_id":1,"name":"Parkour","numb_events":null},{"skill_id":2,"name":"Programming","numb_events":null}]}


// want: a single object for a single person.

public class UserProfile
{
	@SerializedName("name")
	private String userName;
	
	@SerializedName("description")
	private String description;
	
	@SerializedName("skillsOffer")
	private ArrayList<Skill> skillsOffer;
	
	@SerializedName("skillsLearn")
	private ArrayList<Skill> skillsLearn;
	
	@SerializedName("photo_url")
	private String userPicture;		// An url
	
	@SerializedName("location")
	private String location;
	
	@SerializedName("is_male")
	private boolean isMale;
	
	@SerializedName("word_cloud")
	private String[] wordCloud;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<Skill> getSkillsOffer() {
		return skillsOffer;
	}
	public void setSkillsOffer(ArrayList<Skill> skills) {
		this.skillsOffer = skills;
	}
	public ArrayList<Skill> getSkillsLearn() {
		return skillsLearn;
	}
	public void setSkillsLearn(ArrayList<Skill> skills) {
		this.skillsLearn = skills;
	}
	public String getUserPicture() {
		return userPicture;
	}
	public void setUserPicture(String userPicture) {
		this.userPicture = userPicture;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public boolean isMale() {
		return isMale;
	}
	public void setMale(boolean isMale) {
		this.isMale = isMale;
	}
	public String[] getWordCloud() {
		return wordCloud;
	}
	public void setWordCloud(String[] wordCloud) {
		this.wordCloud = wordCloud;
	}
}