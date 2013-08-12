package com.example.hackathon;

import com.example.hackathon.api.Database;

import de.arvidg.exampleactionbartabs.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SharedMenu
{  
  // start at 1000 ensure that we do not interfere with the activity-specific menu items
  public static final int MENU_ABOUT = 1000;
  public static final int MENU_FAQ   = 1001;
  SessionManager session;
  Database database = new Database("http://192.168.1.109:3000/api/", "android", "1234");
//  public static void onCreateOptionsMenu(Menu menu, Context ctx) {
//	  menu.add
//    menu.add(Menu.NONE, MENU_FAQ, Menu.NONE,
//             ctx.getString(R.string.Faq)).setIcon(R.drawable.ic_menu_faq);
//    menu.add(Menu.NONE, MENU_ABOUT, Menu.NONE,
//             ctx.getString(R.string.About)).setIcon(R.drawable.ic_menu_about);
//  }
  public boolean onOptionsItemSelected(MenuItem item, Activity caller) {
	  
	  session = new SessionManager(caller.getApplicationContext());
	  
		switch(item.getItemId()) { 
			case R.id.menuitem_search:
				Toast.makeText(caller.getApplicationContext(), "Search", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menuitem_profile:
				Toast.makeText(caller.getApplicationContext(), "My Profile", Toast.LENGTH_SHORT).show();
				Log.d("SharedMenu", "user id in shared menu is: " + session.getUserDetails().get("id"));
				Intent i = new Intent(caller, PeopleDetail.class);
				Bundle b = new Bundle();
				
				b.putInt("userId", Integer.parseInt(session.getUserDetails().get("id")));
//				i.putExtra("userId", session.getUserDetails().get("id"));
				i.putExtras(b);
				caller.startActivity(i);
				return true;
			case R.id.menuitem_events:
				Toast.makeText(caller.getApplicationContext(), "My Events", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menuitem_create:
				Log.d("SharedMenu", "come into shared menu");
				Toast.makeText(caller.getApplicationContext(), "Create Event", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(caller.getApplicationContext(),EventCreate.class);
				Bundle eventDetails = new Bundle();
				eventDetails.putInt("eventId", -1);
				intent.putExtras(eventDetails);
				caller.startActivity(intent);
				return true;
			case R.id.menuitem_logout:
				Toast.makeText(caller.getApplicationContext(), "Log Out", Toast.LENGTH_SHORT).show();
				Log.d("SharedMenu", "logout user id:" + session.getUserDetails().get("id"));
				database.logoutUser(session.getUserDetails().get("token"));
				session.logoutUser();
				return true;
			case R.id.menuitem_settings:
				Toast.makeText(caller.getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
				//FLAG_ACTIVITY_NO_HISTORY - set flag so press back button in settings take you to home
 				return true;
//			case R.id.menuitem_edit:
//				Toast.makeText(caller.getApplicationContext(), "Edit", Toast.LENGTH_SHORT).show();
//				caller.startActivity(new Intent(caller, EditProfile.class));
//				return true;
		}
		return false;
}
}