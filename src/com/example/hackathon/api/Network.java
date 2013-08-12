//package com.example.hackathon.api;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.example.hackathon.AFragment;
//import com.example.hackathon.BFragment;
//import com.example.hackathon.models.ListEvents;
//import com.example.hackathon.models.ListUsers;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//import android.util.Log;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//public class Network {
//	
//	public static class GetListUsers extends AsyncTask<String,Void,Void>
//	{
//		public ArrayList<ListUsers> user;
//		TextView txtness;
//		AFragment txt;
//		
//		public GetListUsers(AFragment txt){
//			this.txt = txt;
//			user= new ArrayList<ListUsers>();
//		}
//
//		protected Void doInBackground(String... Id) {
//
//			try {
//				String jsonData = getData("http://commplus.iriscouch.com/communityplus/_design/listSkillOffer/_view/listSkillOffer");
//				JSONArray rows = new JSONObject(jsonData).getJSONArray("rows");
//				JSONObject temp = null;
//				for (int i = 0; i<rows.length();i++)
//				{
//					temp = rows.getJSONObject(i);
//					user.add(new ListUsers());
//					user.get(i).setUserID(temp.getString("value"));
////					user.get(i).setSkillOffer(temp.getString("key"));
//					
//					jsonData = getData("http://commplus.iriscouch.com/communityplus/_design/listUsers/_view/listUsers?key=%22" + user.get(i).getUserID() + "%22");
//					JSONArray rows2 = new JSONObject(jsonData).getJSONArray("rows");
//					JSONObject temp2 = rows2.getJSONObject(0);
//					JSONObject temp3 = temp2.getJSONObject("value");
//					//Toast.makeText(this,temp2.getString("name") ,Toast.LENGTH_LONG);
//					user.get(i).setUserName(temp3.getString("name"));
//					
//					Bitmap mIcon11 = null;
//					try {
//						InputStream in = new java.net.URL("http://commplus.iriscouch.com/communityplus/" + user.get(i).getUserID() + "/" + user.get(i).getUserID() + ".jpg").openStream();
//						//InputStream in = new java.net.URL("http://commplus.iriscouch.com/communityplus/b0181fc582b8a8f36affdde8cf001e76/b0181fc582b8a8f36affdde8cf001e76.jpg").openStream();
//						mIcon11 = BitmapFactory.decodeStream(in);
//					} catch (Exception e) {
//						Log.e("Error", e.getMessage());
//						e.printStackTrace();
//					}
//					
////					user.get(i).setUserPicture(mIcon11);
//				}
//			}
//			catch (IOException e) {
//				Log.e("JSON", "cannot find JSON IO", e);
//			} catch (JSONException e) {
//				Log.e("JSON", "cannot find JSON", e);
//			}
//			return null;
//			
//		}
//
//		protected void onPostExecute(Void params) {
//			txt.startAdapter(user);
//		}
//
//	}
//	
//	public static class GetListEvents extends AsyncTask<String,Void,Void>
//	{
//		public ArrayList<ListEvents> events;
//		BFragment frag;
//		
//		public GetListEvents(BFragment frag){
//			this.frag = frag;
//			events= new ArrayList<ListEvents>();
//		}
//
//		protected Void doInBackground(String... Id) {
//
//			try {
//				String jsonData = getData("http://commplus.iriscouch.com/communityplus/_design/listEvents/_view/listEvents");
//				JSONArray rows = new JSONObject(jsonData).getJSONArray("rows");
//				JSONObject row = null;
//				for (int i = 0; i<rows.length();i++)
//				{
//					row = rows.getJSONObject(i);
//					events.add(new ListEvents(row.getString("id"),null,null,row.getString("key"),null));
//					events.get(i).setName(row.getJSONObject("value").getString("name"));
//					events.get(i).setLocation(row.getJSONObject("value").getString("location"));
//				}
//			}
//			catch (IOException e) {
//				Log.e("JSON", "cannot find JSON IO", e);
//			} catch (JSONException e) {
//				Log.e("JSON", "cannot find JSON", e);
//			}
//			return null;
//			
//		}
//
//		protected void onPostExecute(Void params) {
//			frag.startAdapter(events);
//		}
//
//	}
//
//	
//	public static String getData(String url) throws IOException {
//		URL u = new URL(url);
//		HttpURLConnection c = (HttpURLConnection) u.openConnection();
//		c.connect();
//		if (c.getResponseCode() == HttpURLConnection.HTTP_OK) {
//			InputStream is = c.getInputStream();
//			byte[] buf = new byte[1024];
//			int len;
//			ByteArrayOutputStream os = new ByteArrayOutputStream();
//			while ((len = is.read(buf)) > 0) {
//				os.write(buf, 0, len);
//			}
//			is.close();
//			return os.toString();
//		}
//
//		return null;
//	}
//}
