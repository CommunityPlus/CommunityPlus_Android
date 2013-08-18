package com.example.hackathon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackathon.adapter.PeopleAdapter;
import com.example.hackathon.api.Database;
import com.example.hackathon.models.Attendees;
import com.example.hackathon.models.EventProfile;
import com.example.hackathon.models.ListUsers;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import de.arvidg.exampleactionbartabs.R;

public class EventDetail extends FragmentActivity {

	// menu
	protected SharedMenu sharedMenu;
	// Intent mShareIntent;

	// dropdown menu
	static TextView descriptionText = null;
	static boolean IsExpand = false;
	static ImageView ic_expand = null;

	// RSVP
	static Button RSVPbtn = null;
	static AlertDialogManager rsvp_type;
	boolean isTeach = true;

	// components to edit
	static TextView txt;
	LinearLayout AttendanceLayout;
	ListView lv;

	// EventProfile
	Database database;
	EventProfile eventProfile;
	ArrayList<ListUsers> attendees;
	int event_id;

	// Things to Popualte
	private static Date eventDateTime = null;

	// ImageLoader
	static ImageLoader imageLoader;
	DisplayImageOptions options;
	boolean isUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_detail);

		// get eventId and create a new EventProfile accordingly
		Bundle eventDetails = getIntent().getExtras();
		isUser = eventDetails.getBoolean("isUser");

		// Create new UserProfile based on userId (From DB)
		database = new Database("http://192.168.1.109:3000/api/", "android",
				"1234");

		descriptionText = (TextView) findViewById(R.id.event_description);
		ic_expand = (ImageView) findViewById(R.id.ic_expand);
		// need to set the backgroud source here (instead of in XML) otherwise
		// will have duplicates
		ic_expand.setBackgroundResource(R.drawable.ic_expand);
		RSVPbtn = (Button) findViewById(R.id.RSVPbtn);

		// image loader
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher)
				.resetViewBeforeLoading(true).cacheOnDisc(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new RoundedBitmapDisplayer(20)).build();

		imageLoader = ImageLoader.getInstance();
		initImageLoader(getApplicationContext());

		// attendees
		lv = (ListView) findViewById(R.id.listf);
		try {
			event_id = eventDetails.getInt("eventId");
			Log.d("EventDetail", Integer.toString(event_id));

			eventProfile = database.showEventProfile(event_id, getApplicationContext());

			Log.d("EventDetail", "event name is: " + eventProfile.getName());
			attendees = eventProfile.getAttendees();
			PeopleAdapter peopleAdapter = new PeopleAdapter(
					getApplicationContext(), attendees, imageLoader);
			lv.setAdapter(peopleAdapter);
			lv.setOnItemClickListener(clickListener);
		} catch (InterruptedException e) {
			Log.e("Database", "Throw InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			Log.e("Database", "Throw ExecutionException");
			e.printStackTrace();
		}

		sharedMenu = new SharedMenu();

		// // for share stuff, don't need
		// // mShareIntent = new Intent();
		// // mShareIntent.setAction(Intent.ACTION_SEND);
		// // mShareIntent.setType("text/plain");
		// // mShareIntent.putExtra(Intent.EXTRA_TEXT,
		// // "From me to you, this text is new.");

		populateEventDetail();
		RSVPStatus();
		setEventPic();

	}

	private void RSVPStatus() {
		if (eventProfile.getAttendeeId() == 0) {
			RSVPbtn.setText("RSVP");
		}
		else{
			RSVPbtn.setText("Going!");
		}
	}

	private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int pos,
				long l) {
			try {
				ListUsers userSelected = (ListUsers) attendees.get(pos);

				Toast.makeText(
						getApplicationContext(),
						"You selected: " + userSelected.getUserName()
								+ "at userId: " + userSelected.getUserID(),
						Toast.LENGTH_SHORT).show();
				Bundle userDetails = new Bundle();
				userDetails.putInt("userId", userSelected.getUserID());

				Intent intent = new Intent(getApplicationContext(),
						PeopleDetail.class);
				intent.putExtras(userDetails);
				startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private String EVENT_DATE_TIME;

	private void setEventPic() {
		ImageView imageView = (ImageView) findViewById(R.id.eventPicutreImage);

		imageLoader.displayImage(eventProfile.getEventPicture(), imageView,
				options, new SimpleImageLoadingListener());
	}

	private String getDayOfWeek(int day) {

		switch (day) {
		case 1:
			return "Sun";
		case 2:
			return "Mon";
		case 3:
			return "Tue";
		case 4:
			return "Wed";
		case 5:
			return "Thu";
		case 6:
			return "Fri";
		case 7:
			return "Sat";
		}
		return null;
	}

	private void formatDateTime() {
		// EVENT_DATE_TIME = EVENT_DATE + " | " + EVENT_TIME;
		// System.out.println("evnet date time is: " + EVENT_DATE_TIME);
		int year = eventDateTime.getYear() + 1900;
		int month = eventDateTime.getMonth() + 1;

		String day = getDayOfWeek(eventDateTime.getDay());

		EVENT_DATE_TIME = day + "  |  " + eventDateTime.getDate() + " / "
				+ month + " / " + year + "  |  " + eventDateTime.getHours()
				+ " : " + eventDateTime.getMinutes();
		Log.d("EventCreate", "after format date time: " + EVENT_DATE_TIME);
	}

	private void getDateTime() {
		// EVENT_DATE =
		String DateTime = eventProfile.getDate() + " " + eventProfile.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			eventDateTime = format.parse(DateTime);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
	}

	private void populateEventDetail() {
		txt = (TextView) findViewById(R.id.event_name);
		txt.setText(eventProfile.getName());
		txt = (TextView) findViewById(R.id.event_location);
		txt.setText(eventProfile.getLocation());
		txt = (TextView) findViewById(R.id.event_description);
		txt.setText(eventProfile.getDescription());

		// Find # of attendees, date, time
		getDateTime();
		txt = (TextView) findViewById(R.id.event_date_time);
		formatDateTime();
		txt.setText(EVENT_DATE_TIME);

		txt = (TextView) findViewById(R.id.event_attendance);
		if (eventProfile.getAttendees().size() < 2) {
			txt.setText(Integer.toString(eventProfile.getAttendees().size())
					+ "  Person Attending");
		} else
			txt.setText(Integer.toString(eventProfile.getAttendees().size())
					+ "  People Attending");
	}

	public static void initImageLoader(Context context) {

		imageLoader.destroy();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging()
				.build();

		ImageLoader.getInstance().init(config);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		if (isUser == true)
			inflater.inflate(R.menu.edit, menu);
		else
			inflater.inflate(R.menu.main, menu);
		// MenuItem item = menu.findItem(R.id.menuitem_share);
		// // Get its ShareActionProvider
		// ShareActionProvider mShareActionProvider = (ShareActionProvider)
		// item.getActionProvider();

		// Connect the dots: give the ShareActionProvider its Share Intent
		// if (mShareActionProvider != null) {
		// mShareActionProvider.setShareIntent(mShareIntent);
		// }
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (sharedMenu.onOptionsItemSelected(item, this) == false) {
			// handle local menu items here or leave blank
			Toast.makeText(EventDetail.this, "Edit", Toast.LENGTH_SHORT).show();
			Intent i;
			i = new Intent(EventDetail.this, EventCreate.class);
			i.putExtra("eventId", event_id);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);

	}

	// Toggle expand depending on the status of IsExpand (with default false)
	public void ExpandOnClick(View v) {
		if (IsExpand == false) {
			descriptionText.setMaxLines(Integer.MAX_VALUE);
			IsExpand = true;
			ic_expand.setBackgroundResource(R.drawable.ic_stat_collapse); // ic_stat_collapse

		} else {
			descriptionText.setMaxLines(2);
			IsExpand = false;
			ic_expand.setBackgroundResource(R.drawable.ic_expand);
		}

	}

	// Matt: Put code here to upload individual RSVP status to cloud
	public void RsvpStatus(View v) {

		SessionManager session = new SessionManager(getApplicationContext());

		// make sure that attendee is not going 
		if (eventProfile.getAttendeeId() == 0) {	//RSVPstatus == false && 
			RSVPbtn.setText("Going!");

			// Source of the data in the DIalog
			CharSequence[] array = {"To Teach", "To Learn"};

			AlertDialog ad = new AlertDialog.Builder(this)
					.setCancelable(false)
					.setTitle("Select who you are")
					.setSingleChoiceItems(array, 1,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Log.d("EventDetail",
											"set single choice button 1: "
													+ which);

									switch (which) {
									case 0:
										isTeach = true;
									case 1:
										isTeach = false;
									}
								}
							})
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									//do nothing here...
								}
							}).create();
			ad.show();

			Attendees attendee = new Attendees();
			attendee.setEvent_id(event_id);
			attendee.setUser_id(Integer.parseInt(session.getUserDetails().get(
					"id")));
			attendee.setIs_Teach(isTeach);
			database.createAttendee(attendee);

		} else {
			RSVPbtn.setText("RSVP");
			if (eventProfile.getAttendeeId() > 0){	//going
				database.deleteAttendee(eventProfile.getAttendeeId());
			}

		}
		
		// update list view
		try {
			eventProfile = database.showEventProfile(event_id, getApplicationContext());
			attendees = eventProfile.getAttendees();
			PeopleAdapter peopleAdapter = new PeopleAdapter(
					getApplicationContext(), attendees, imageLoader);
			lv.setAdapter(peopleAdapter);
			lv.setOnItemClickListener(clickListener);
		} catch (InterruptedException e) {
			Log.e("Database", "Throw InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			Log.e("Database", "Throw ExecutionException");
			e.printStackTrace();
		}
		
		// update # of people attending
		txt = (TextView) findViewById(R.id.event_attendance);
		if (eventProfile.getAttendees().size() < 2) {
			txt.setText(Integer.toString(eventProfile.getAttendees().size())
					+ "  Person Attending");
		} else
			txt.setText(Integer.toString(eventProfile.getAttendees().size())
					+ "  People Attending");
		
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.d("EventDetail", "on restart 1");
		finish();
		Log.d("EventDetail", "on restart 2");
		startActivity(getIntent());
		Log.d("EventDetail", "on restart 3");
	}

}
