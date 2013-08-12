package com.example.hackathon;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;

public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;

	// Editor for Shared preferences
	Editor editor;

	// Context
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Sharedpref file name
	private static final String PREF_NAME = "MyPreferences";

	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";

	// User name (make variable public to access from outside)
	public static final String KEY_NAME = "name";

	// Email address (make variable public to access from outside)
	private static final String KEY_EMAIL = "email";
	private static final String KEY_ID = "id";

	// authentication token for Matt
	private static final String KEY_TOKEN = "token";
	private static String token;
	private static Boolean LoggedIn;

	// Constructor
	public SessionManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
//		isLoggedIn = false;
	}

	/**
	 * Create login session
	 * */
	// public void createLoginSession(String name, String email, int id) {
	public void createLoginSession(String token, String id) {

		// Storing email in pref
		editor.putString(KEY_TOKEN, token);

		// Storing id in pref
		editor.putString(KEY_ID, id);

		// commit changes
		editor.commit();
	}

	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails() {
		// HashMap<String, String> user = new HashMap<String, String>();
		// // user name
		// user.put(KEY_NAME, pref.getString(KEY_NAME, null));
		//
		// // user email id
		// user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

		HashMap<String, String>  user = new HashMap<String, String>();

		// user name
		user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));

		// user id
		user.put(KEY_ID, pref.getString(KEY_ID, null));

		Log.d("SessionManager", "saved token is: " + user.get(KEY_TOKEN)
				+ ", saved id is: " + user.get(KEY_ID));

		return user;
	}

	/**
	 * Check login method wil check user login status If false it will redirect
	 * user to login page Else won't do anything
	 * */
	// public void checkLogin() {
	public Boolean checkLogin() {
		// Check login status
		Log.d("SessionManager", "does it come here?");
		if (!this.isLoggedIn()) {
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(_context, LoginActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			// Staring Login Activity
			_context.startActivity(i);
			return false;
		}
		return true;

	}

	/**
	 * Clear session details
	 * */
	public void logoutUser() {
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();

		// After logout redirect user to Loing Activity
		Intent i = new Intent(_context, LoginActivity.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		// Staring Login Activity
		_context.startActivity(i);
		Log.d("SessionManager", "logout user sucessfully");
	}

	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn() {
		//don't know how to use this IS_LOGIN crap..
		return pref.getBoolean(IS_LOGIN, false);
	}

	public void saveToken(String auth_token, String user_id, Boolean isSuccess) {
		
		//store login status
		editor.putBoolean(IS_LOGIN, isSuccess);
//		LoggedIn = isSuccess;
		editor.commit();
		Log.d("SessionManager", "user login status; " + LoggedIn); //user.get(IS_LOGIN)
		
		if (isSuccess ==true){
			Log.d("SessionManager", "token is: " + auth_token);
			Log.d("SessionManager", "id is: " + user_id);
			Log.d("SessionManager", "status is: " + LoggedIn);
			createLoginSession(auth_token, user_id);
		}
		else{
			Log.e("SessionManager", "invalid username or password");
		}
		
//		getUserDetails();
	}
}
