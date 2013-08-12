package com.example.hackathon.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.example.hackathon.SessionManager;
import com.example.hackathon.models.EventProfile;
import com.example.hackathon.models.ListEvents;
import com.example.hackathon.models.ListUsers;
import com.example.hackathon.models.Skill;
import com.example.hackathon.models.UserProfile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Class for communicating with the Community+ database. The interface to the
 * database must be specified by an absolute path in <code>url</code>.
 * <p>
 * Example usage: <code>
 * UserProfile user;
 * user = .....
 * Database database = new Database("http://communityplus.herokuapp.com",user","pass");
 * database.createUser(user);
 * </code>
 * 
 */
public class Database {
	/**
	 * Define ArrayList types to be used in the Gson deserialization.
	 */
	Type ListUsersArray;
	Type ListEventsArray;

	/**
	 * Http Basic authentication details.
	 */
	String username;
	String password;

	String url;

	public Database(String url, String username, String password) {
		this.url = url;
		this.password = password;
		this.username = username;

		ListUsersArray = new TypeToken<ArrayList<ListUsers>>() {
		}.getType();
		ListEventsArray = new TypeToken<ArrayList<ListEvents>>() {
		}.getType();
		/*
		 * UserProfile user = new UserProfile();
		 * 
		 * user.name="Bob144"; user.skills = new ArrayList<Skill>();
		 * user.description = "Bob is caucasian";
		 * 
		 * createUser(user);
		 */
	}

	/**
	 * Inserts a new user in the database. The user must be specified using a
	 * <code>UserProfile</code> object.
	 * <p>
	 * This method always returns immediately, whether or not the command was
	 * server-side action was completed successfully or not.
	 * 
	 * @param user
	 *            a user object to be inserted in the database
	 * @see UserProfile
	 */
	public void createUser(UserProfile user) {
		String item = "users/";
		Gson gson = new Gson();
		String serializedObject = gson.toJson(user);
		new SendCreate().execute(item, serializedObject);
	}

	public void logoutUser(String token)

	{

		// DELETE
		// http://localhost:3000/v1/sessions/\?auth_token\=JRYodzXgrLsk157ioYHf

		String item = "sessions/?auth_token=" + token;

		new SendDelete().execute(item);

	}

	public void loginUser(final Context context, String email, String pwd)
	// curl -v -H 'Content-Type: application/json' -H 'Accept: application/json'
	// -X POST http://localhost:3000/api/sessions -d
	// "{\"user\":{\"email\":\"looi.matthew1@gmail.com\",\"password\":\"password\"}}"
	{
		Gson gson = new Gson();
		String serializedObject = "{ \"user\": { \"email\": "
				+ gson.toJson(email) + ", \"password\": " + gson.toJson(pwd)
				+ " }}";
		new AsyncTask<String, Void, String>() {
			@Override
			protected String doInBackground(String... params) {
				String item = params[0];
				String serializedJson = params[1];

				try {
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httpput = new HttpPost(url + item);
					Log.d("login", url + item);
					httpput.setHeader("Content-Type", "application/json");
					httpput.setHeader("Accept", "application/json");

					Log.d("login", "sending " + serializedJson);
					StringEntity entity = new StringEntity(serializedJson);
					httpput.setEntity(entity);
					String authorizationString = "Basic "
							+ Base64.encodeToString(
									(username + ":" + password).getBytes(),
									Base64.NO_WRAP);
					httpput.setHeader("Authorization", authorizationString);

					HttpResponse result = httpclient.execute(httpput);

					if (result == null) {
						// something is wrong
					} else {
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						try {
							InputStream is = result.getEntity().getContent();
							int c;
							while ((c = is.read()) >= 0) {
								os.write(c);
							}
							is.close();
							os.close();
							return os.toString();
						} catch (IOException e1) {
							Log.e("login", "read error", e1);
						}
					}

				} catch (Exception e) {
					Log.e("log_tag", "Error in http connection " + e.toString());
				}

				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				// {"success":true,"info":"Logged in","data":{"auth_token":"AWfmLXCCsExWkUQTPVEL"}}*
				// Closing connection #0

				Log.d("login", "got back: " + result);
				SessionManager sm = new SessionManager(context);

				try {
					String token = new JSONObject(result).getJSONObject("data")
							.getString("auth_token");
					int id = new JSONObject(result).getJSONObject("data")
							.getInt("id");
					Boolean success = new JSONObject(result)
							.getBoolean("success");
					sm.saveToken(token, Integer.toString(id), success);
				} catch (JSONException e) {
					Log.e("login", "json error", e);
				}

			}
		}.execute("sessions", serializedObject);

	}

	/**
	 * Adds a new skill to the database. The skill must be specified using a
	 * <code>Skill</code> object.
	 * <p>
	 * This method always returns immediately, whether or not the command was
	 * server-side action was completed successfully or not.
	 * 
	 * @param skill
	 *            a skill object to be inserted in the database
	 * @see Skill
	 */
	public void createSkills(Skill skill) {
		String item = "skills/";
		new SendCreate().execute(item, skill.toString());
	}

	/**
	 * Adds a new event to the database. The event must be specified using a
	 * <code>EventProfile</code> object.
	 * <p>
	 * This method always returns immediately, whether or not the command was
	 * server-side action was completed successfully or not.
	 * 
	 * @param event
	 *            a event object to be inserted in the database
	 * @see EventProfile
	 */
	public void createEvents(EventProfile event) {
		String item = "events/";
		new SendCreate().execute(item, event.toString());
	}

	/**
	 * Retrieves a list of all users in the database.
	 * <p>
	 * This method will freeze the calling thread to wait for a result to be
	 * returned by the <code>SendShow</code> <code>AsyncTask</code>. As such,
	 * the method should never be called directly from the UI thread.
	 * 
	 * @return users List of users
	 * @see ListUsers
	 * @throws ExecutionException
	 * @throws InterruptedException
	 **/
	public ArrayList<ListUsers> showListUsers() throws InterruptedException,
			ExecutionException {
		String item = "users.json";
		SendShow request = new SendShow();
		request.execute(item);

		String json = request.get();
		Gson gson = new Gson();
		ArrayList<ListUsers> users = gson.fromJson(json, ListUsersArray);
		// Log.d("DatabaseDebug", json);
		return users;
	}

	/**
	 * Retrieves a single user profile from the database.
	 * <p>
	 * This method will freeze the calling thread to wait for a result to be
	 * returned by the <code>SendShow</code> <code>AsyncTask</code>. As such,
	 * the method should never be called directly from the UI thread.
	 * 
	 * @return user the single user's profile
	 * @see UserProfile
	 * @throws ExecutionException
	 * @throws InterruptedException
	 **/
	public UserProfile showUserProfile(int id) throws InterruptedException,
			ExecutionException {
		String item = "users/" + id + ".json";
		SendShow request = new SendShow();
		request.execute(item);

		String json = request.get();
		Gson gson = new Gson();
		UserProfile user = gson.fromJson(json, UserProfile.class);
		return user;
	}

	/**
	 * Retrieves a list of all events in the database.
	 * <p>
	 * This method will freeze the calling thread to wait for a result to be
	 * returned by the <code>SendShow</code> <code>AsyncTask</code>. As such,
	 * the method should never be called directly from the UI thread.
	 * 
	 * @return events List of events
	 * @see ListEvents
	 * @throws ExecutionException
	 * @throws InterruptedException
	 **/
	public ArrayList<ListEvents> showListEvents() throws InterruptedException,
			ExecutionException {
		String item = "events.json";
		SendShow request = new SendShow();
		request.execute(item);

		String json = request.get();
		Gson gson = new Gson();
		ArrayList<ListEvents> events = gson.fromJson(json, ListEventsArray);
		Log.d("DatabaseDebug", json);
		return events;
	}

	/**
	 * Retrieves a single event profile from the database.
	 * <p>
	 * This method will freeze the calling thread to wait for a result to be
	 * returned by the <code>SendShow</code> <code>AsyncTask</code>. As such,
	 * the method should never be called directly from the UI thread.
	 * 
	 * @return event the event's profile
	 * @see EventProfile
	 * @throws ExecutionException
	 * @throws InterruptedException
	 **/
	public EventProfile showEventProfile(int id) throws InterruptedException,
			ExecutionException {
		String item = "events/" + id + ".json";
		SendShow request = new SendShow();
		request.execute(item);

		Log.d("Database", "Event id in showEventProfile is: " + id);
		String json = request.get();
		Gson gson = new Gson();
		EventProfile event = gson.fromJson(json, EventProfile.class);
		Log.d("Database", "Event name in ShowEventProfile is: " + event.getName());
		return event;
	}

	/**
	 * Deletes a single user from the database. The user must be specified by
	 * his/her unique record <code>id</code>.
	 * <p>
	 * This method always returns immediately, whether or not the command was
	 * server-side action was completed successfully or not.
	 * 
	 * @param id
	 *            the user's id
	 **/
	public void deleteUser(int id) {
		String item = "users/" + id;
		new SendDelete().execute(item);
	}

	/**
	 * Deletes a single skill entry from the database. The skill must be
	 * specified by a unique record <code>id</code>.
	 * <p>
	 * This method always returns immediately, whether or not the command was
	 * server-side action was completed successfully or not.
	 * 
	 * @param id
	 *            the skill's id
	 **/
	public void deleteSkill(int id) {
		String item = "skills/" + id;
		new SendDelete().execute(item);
	}

	/**
	 * Deletes a single attendee from the database. The attendee must be
	 * specified by a unique record <code>id</code>.
	 * <p>
	 * This method always returns immediately, whether or not the command was
	 * server-side action was completed successfully or not.
	 * 
	 * @param id
	 *            the attendee's id
	 * @todo implement the attendee methods
	 **/
	public void deleteAttendee(int id) {
		String item = "attendees/" + id;
		new SendDelete().execute(item);
	}

	/**
	 * Deletes a single event from the database. The event must be specified by
	 * a unique record <code>id</code>.
	 * <p>
	 * This method always returns immediately, whether or not the command was
	 * server-side action was completed successfully or not.
	 * 
	 * @param id
	 *            the event's id
	 **/
	public void deleteEvent(int id) {
		String item = "events/" + id;
		new SendDelete().execute(item);
	}

	/**
	 * Updates the details of a single user from the database. The user must be
	 * specified by a unique record <code>id</code>.
	 * <p>
	 * The new details must be specified using a <code>UserProfile</code>
	 * object.
	 * <p>
	 * This method always returns immediately, whether or not the command was
	 * server-side action was completed successfully or not.
	 * 
	 * @param id
	 *            the user to be updated
	 * @param user
	 *            a user object containing the updated details
	 * @see UserProfile
	 **/
	public void updateUser(int id, UserProfile user) {
		String item = "users/" + id;
		new SendUpdate().execute(item, user.toString());
	}

	/**
	 * Updates the details of a single skill from the database. The skill must
	 * be specified by a unique record <code>id</code>.
	 * <p>
	 * The new details must be specified using a <code>Skill</code> object.
	 * <p>
	 * This method always returns immediately, whether or not the command was
	 * server-side action was completed successfully or not.
	 * 
	 * @param id
	 *            the skill to be updated
	 * @param skill
	 *            a skill object containing the updated details
	 * @see Skill
	 **/
	public void updateSkills(int id, Skill skill) {
		String item = "skills/" + id;
		new SendUpdate().execute(item, skill.toString());
	}

	/**
	 * Updates the details of a single event in the database. The event must be
	 * specified by a unique record <code>id</code>.
	 * <p>
	 * The new details must be specified using a <code>EventProfile</code>
	 * object.
	 * <p>
	 * This method always returns immediately, whether or not the command was
	 * server-side action was completed successfully or not.
	 * 
	 * @param id
	 *            the event to be updated
	 * @param event
	 *            an event object containing the updated details
	 * @see EventProfile
	 **/
	public void updateEvents(int id, EventProfile event) {
		String item = "events/" + id;
		new SendUpdate().execute(item, event.toString());
	}

	// public void updateAttendees(int id, Attendee attendee)
	// public void updateAttendees(int user_id, EventProfile event, UserProfile
	// user)
	// {
	// String item = "attendees/"+id;
	// new SendUpdate().execute(item, attendee.toString());
	// }

	/**
	 * Generic <code>AsyncTask</code> for sending Http GET requests to the
	 * Community+ database. These requests are used for all <i>Show</i> queries
	 * that retrieve records from the database. The database is specified by
	 * <code>url</code>
	 * 
	 * @see url
	 **/
	private class SendShow extends AsyncTask<String, Void, String> {
		protected void onPreExecute() {
		}

		protected String doInBackground(String... params) {
			String item = params[0];
			String json = "";
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(url + item);
				httpget.setHeader("Content-Type", "application/json");
				httpget.setHeader("Accept", "application/json");

				String authorizationString = "Basic "
						+ Base64.encodeToString(
								(username + ":" + password).getBytes(),
								Base64.NO_WRAP);
				httpget.setHeader("Authorization", authorizationString);

				HttpResponse httpResponse = httpclient.execute(httpget);

				Scanner scanner = new Scanner(httpResponse.getEntity()
						.getContent(), "UTF-8");

				while (scanner.hasNextLine()) {
					json += scanner.nextLine() + "\n";
				}
			} catch (Exception e) {
				Log.e("log_tag", "Error in http connection " + e.toString());
			}

			Log.d("Database", "SendShow json: " + json);
			return json;
		}

		protected void onPostExecute(Void unused) {

		}

	}

	/**
	 * Generic <code>AsyncTask</code> for sending Http POST requests to the
	 * Community+ database. These requests are used for all <i>Create</i>
	 * queries that insert new records in the database. The database is
	 * specified by <code>url</code>
	 * 
	 * @see url
	 **/
	private class SendCreate extends AsyncTask<String, Void, Void> {
		HttpResponse response;

		protected void onPreExecute() {
		}

		protected Void doInBackground(String... params) {
			String item = params[0];
			String serializedJson = params[1];

			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url + item);
				httppost.setHeader("Content-Type", "application/json");
				httppost.setHeader("Accept", "application/json");

				StringEntity entity = new StringEntity(serializedJson);
				httppost.setEntity(entity);

				String authorizationString = "Basic "
						+ Base64.encodeToString(
								(username + ":" + password).getBytes(),
								Base64.NO_WRAP);
				httppost.setHeader("Authorization", authorizationString);

				response = httpclient.execute(httppost);
			} catch (Exception e) {
				Log.e("log_tag", "Error in http connection " + e.toString());
			}

			return null;
		}

		protected void onPostExecute(Void unused) {
			String result = "";
			try {
				result = EntityUtils.toString(response.getEntity());
			} catch (Exception e) {
			}

		}

	}

	/**
	 * Generic <code>AsyncTask</code> for sending Http DELETE requests to the
	 * Community+ database. These requests are used for all <i>Delete</i>
	 * queries that remove records from the database. The database is specified
	 * by <code>url</code>
	 * 
	 * @see url
	 **/
	private class SendDelete extends AsyncTask<String, Void, Void> {
		protected void onPreExecute() {
		}

		protected Void doInBackground(String... params) {
			String item = params[0];

			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpDelete httpdelete = new HttpDelete(url + item);
				httpdelete.setHeader("Content-Type", "application/json");
				httpdelete.setHeader("Accept", "application/json");

				String authorizationString = "Basic "
						+ Base64.encodeToString(
								(username + ":" + password).getBytes(),
								Base64.NO_WRAP);
				httpdelete.setHeader("Authorization", authorizationString);

				httpclient.execute(httpdelete);
			} catch (Exception e) {
				Log.e("log_tag", "Error in http connection " + e.toString());
			}

			return null;
		}

		protected void onPostExecute(Void unused) {

		}

	}

	/**
	 * Generic <code>AsyncTask</code> for sending Http PUT requests to the
	 * Community+ database. These requests are used for all <i>Update</i>
	 * queries that update record information in the database. The database is
	 * specified by <code>url</code>
	 * 
	 * @see url
	 **/
	private class SendUpdate extends AsyncTask<String, Void, Void> {
		protected void onPreExecute() {
		}

		protected Void doInBackground(String... params) {
			String item = params[0];
			String serializedJson = params[1];

			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPut httpput = new HttpPut(url + item);
				httpput.setHeader("Content-Type", "application/json");
				httpput.setHeader("Accept", "application/json");

				StringEntity entity = new StringEntity(serializedJson);
				httpput.setEntity(entity);

				String authorizationString = "Basic "
						+ Base64.encodeToString(
								(username + ":" + password).getBytes(),
								Base64.NO_WRAP);
				httpput.setHeader("Authorization", authorizationString);

				httpclient.execute(httpput);
			} catch (Exception e) {
				Log.e("log_tag", "Error in http connection " + e.toString());
			}

			return null;
		}

		protected void onPostExecute(Void unused) {

		}
	}

	private class SendLogin extends AsyncTask<String, Void, HttpResponse> {
		protected void onPreExecute() {
		}

		protected HttpResponse doInBackground(String... params) {
			String item = params[0];
			String serializedJson = params[1];

			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httpput = new HttpPost(url + item);
				httpput.setHeader("Content-Type", "application/json");
				httpput.setHeader("Accept", "application/json");

				StringEntity entity = new StringEntity(serializedJson);
				httpput.setEntity(entity);
				//
				// String authorizationString = "Basic " +
				// Base64.encodeToString((username + ":" + password).getBytes(),
				// Base64.NO_WRAP);
				// httpput.setHeader("Authorization", authorizationString);

				return httpclient.execute(httpput);
			} catch (Exception e) {
				Log.e("log_tag", "Error in http connection " + e.toString());
			}

			return null;
		}

		protected void onPostExecute(HttpResponse result) {

		}
	}
}
