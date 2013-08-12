package com.example.hackathon.models;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

import android.util.Log;

public class ListUsers {

	// private userID; // This provides a link to the userProfile;
	@SerializedName("id")
	private int userID;
	@SerializedName("photo_url")
	private String userPicture; // An url
	@SerializedName("skills")
	private ArrayList<String> skillOffer; // This is made a single, and can be made to be
	@SerializedName("name")							// an array
	private String userName;

	public ListUsers(int userID, String userName, ArrayList<String> skillOffer,
			String userPicture) {
		super();
		this.userID = userID;
		this.userPicture = userPicture;
		this.skillOffer = skillOffer;
		this.userName = userName;
		
		Log.d("userId in constructor:", " " + userID);
	}

	public String skillsToString(String[] stringToBreak) {
		String result = "";

		for (String s : stringToBreak) {
			result = result + '#' + s;
		}
		return result;
	}

//	public ListUsers() {
//	};

//	@Override
//	public String toString() {
//		return "ListUsers [userID=" + userID + ", userPicture=" + userPicture
//				+ ", skillOffer=" + skillOffer + ", userName=" + userName + "]";
//	}

	public int getUserID() {
		Log.d("userId:", " "+userID);
		return userID;
	}

	public String getUserPicture() {
		return userPicture;
	}

	public String getSkillOffer() {
		String skillsToOffer = "#";
		
		for (int i=0; i < skillOffer.size(); i++){
			skillsToOffer += skillOffer.get(i);
			skillsToOffer += " #";
		}
		return skillsToOffer;
	}
	
	public String getUserName() {
		return userName;
	}
}
