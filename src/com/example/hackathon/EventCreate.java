package com.example.hackathon;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ParseException;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hackathon.api.Database;
import com.example.hackathon.models.EventProfile;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import de.arvidg.exampleactionbartabs.R;

public class EventCreate extends Activity { // FragmentActivity

	// drop down menu
	static TextView descriptionText = null;
	static boolean IsExpand = false;
	static ImageView ic_expand = null;

	// general stuff
	static TextView txtView = null;
	static Button SaveBtn = null;
	static TextView dialog_txt;

	// stuff that needs to be saved
	private String EVENT_ORGANIZER = null;
	private String EVENT_NAME = null;
	private String EVENT_LOCATION = null;
	private static String EVENT_DATE_TIME = null;
	private String EVENT_DESCRIPTION = null;
	private static String EVENT_DATE = null;
	private static String EVENT_TIME = null;
	protected SharedMenu sharedMenu;

	private static Date eventDateTime = null;

	// if it's an existing event
	static ImageLoader imageLoader;
	DisplayImageOptions options;
	EventProfile eventProfile;
	static int eventId;
	Database database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_create);

		Bundle eventDetails = getIntent().getExtras();

		database = new Database("http://192.168.1.109:3000/api/", "android",
				"1234");

		// image loader
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher)
				.resetViewBeforeLoading(true).cacheOnDisc(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new RoundedBitmapDisplayer(20)).build();

		// brand new event has eventId -1
		// check if it is an existing event
		if (eventDetails.getInt("eventId") > 0) {
			eventId = eventDetails.getInt("eventId");
			try {

				int event_id = eventDetails.getInt("eventId");
				eventProfile = database.showEventProfile(event_id,
						getApplicationContext());

				this.EVENT_NAME = eventProfile.getName();
				this.EVENT_DESCRIPTION = eventProfile.getDescription();
				this.EVENT_DATE = eventProfile.getDate();
				this.EVENT_TIME = eventProfile.getTime();
				this.EVENT_LOCATION = eventProfile.getLocation();
				populateEventDetail();
				setEventPic();
			} catch (InterruptedException e) {
				Log.e("EventCreate", "create event failed");
				e.printStackTrace();
			} catch (ExecutionException e) {
				Log.e("EventCreate", "create event failed");
				e.printStackTrace();
			}
		} else {
			eventId = -1;
			eventProfile = new EventProfile();
		}

		descriptionText = (TextView) findViewById(R.id.event_description);
		ic_expand = (ImageView) findViewById(R.id.ic_expand);
		ic_expand.setBackgroundResource(R.drawable.ic_expand);

		sharedMenu = new SharedMenu();
	}

	private void getDateTime() {
		// EVENT_DATE =
		String DateTime = EVENT_DATE + " " + EVENT_TIME;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			eventDateTime = format.parse(DateTime);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
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

	private void setEventPic() {

		imageLoader = ImageLoader.getInstance();
		initImageLoader(getApplicationContext());

		ImageView imageView = (ImageView) findViewById(R.id.eventPicutreImage);

		imageLoader.displayImage(eventProfile.getEventPicture(), imageView,
				options, new SimpleImageLoadingListener());
	}

	private static void formatDateTime() {
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

	private static String getDayOfWeek(int day) {

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

	private void populateEventDetail() {
		TextView txt = (TextView) findViewById(R.id.event_name);
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

		// Find who's the host

		txt = (TextView) findViewById(R.id.event_organizer);
		txt.setText(eventProfile.getHostName());
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.normal, menu); // don't know why main doesn't
												// work anymore
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (sharedMenu.onOptionsItemSelected(item, this) == false) {
			// handle local menu items here or leave blank
		}
		return super.onOptionsItemSelected(item);
	}

	// Toggle expand depending on the status of IsExpand (with default false)
	public void ExpandOnClick(View v) {
		if (IsExpand == false) {

			System.out.println("COme inside expand IsExpand==false");
			descriptionText.setMaxLines(Integer.MAX_VALUE);
			IsExpand = true;
			ic_expand.setBackgroundResource(R.drawable.ic_stat_collapse); // ic_stat_collapse

		} else {
			descriptionText.setMaxLines(2);
			IsExpand = false;
			ic_expand.setBackgroundResource(R.drawable.ic_expand);
			System.out.println("COme inside expand ELSE");
		}

	}

	public void EditFields(View v) {

		txtView = null;
		txtView = (TextView) findViewById(v.getId());

		// get prompts.xml view
		LayoutInflater li = LayoutInflater.from(EventCreate.this);
		View dialogBox = li.inflate(R.layout.dialog_box, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				new ContextThemeWrapper(EventCreate.this,
						R.style.AlertDialogCustom));
		// alertDialogBuilder.setTitle("Edit " + v.getContentDescription());
		dialog_txt = (TextView) dialogBox.findViewById(R.id.dialog_title);
		dialog_txt.setText(v.getContentDescription());

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(dialogBox);
		final EditText userInput = (EditText) dialogBox
				.findViewById(R.id.user_input);
		// title = (EditText) dialogBox.findViewById(R.id.dialog_title);

		if (txtView == null) {
			userInput.setText(userInput.getText());
		} else {
			userInput.setText(txtView.getText());
			userInput.setText(userInput.getText());
		}

		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// get user input and set it to result edit text
						txtView.setText(userInput.getText());

					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}

	public void ShowTimePickerDialog(View v) {

		dialog_txt = (TextView) findViewById(R.id.event_date_time);
		
		// This is just a declaration.
		DialogFragment userTime = new DatePickerFrag();
		DialogFragment userDate = new DatePickerFrag();

		// Check if the event is exisisting or not.
		if (eventId > 0) { // Pass in with the existing arguements.
			userTime = TimePickerFrag.newInstance(false,
					eventDateTime.getHours(), eventDateTime.getMinutes(), dialog_txt);
			userDate = DatePickerFrag.newInstance(false,
					eventDateTime.getDate(), eventDateTime.getMonth() + 1,
					eventDateTime.getYear() + 1900, dialog_txt);
		} else {
			userTime = TimePickerFrag.newInstance(true, 0, 0, dialog_txt);
			userDate = DatePickerFrag.newInstance(true, 1, 0, 1900, dialog_txt);
		}
		// This is the part where it visibly shows.
		userTime.show(getFragmentManager(), "timePicker");
		userDate.show(getFragmentManager(), "datePicker");
		// To get here. The user must click okay from both the dialog boxes.
		
	}

	public void SaveEvent(View v) {

		txtView = null; // just to clear, doesn't really need to
		Toast.makeText(getApplicationContext(), "Event saved",
				Toast.LENGTH_SHORT).show();

		// super inefficient but whatever
		txtView = (TextView) findViewById(R.id.event_organizer);
		EVENT_ORGANIZER = txtView.getText().toString();
		txtView = (TextView) findViewById(R.id.event_name);
		EVENT_NAME = (String) txtView.getText().toString();
		txtView = (TextView) findViewById(R.id.event_location);
		EVENT_LOCATION = (String) txtView.getText().toString();
		txtView = (TextView) findViewById(R.id.event_date_time);
		EVENT_DATE_TIME = (String) txtView.getText().toString();
		txtView = (TextView) findViewById(R.id.event_description);
		EVENT_DESCRIPTION = (String) txtView.getText().toString();

		// put all the necessary stuff into event profile
		eventProfile.setHostName(EVENT_ORGANIZER);
		eventProfile.setName(EVENT_NAME);
		eventProfile.setDescription(EVENT_DESCRIPTION);
		eventProfile.setLocation(EVENT_LOCATION);
		eventProfile.setTime(eventDateTime.getHours() + ":"
				+ eventDateTime.getMinutes());

		int year = eventDateTime.getYear() + 1900;
		int month = eventDateTime.getMonth() + 1;
		eventProfile
				.setDate(year + "-" + month + "-" + eventDateTime.getDate());

		// Matt has to save event here
		// if event exists, choose update event, else choose save event
		// check if it is an existing event
		if (eventId == -1) {
			database.createEvents(eventProfile);
			finish();

			// EventProfile event = new EventProfile();
			// Bundle eventDetails = new Bundle();
			// eventDetails.putInt("eventId", eventSelected.getEventId());
			//
			// Intent intent = new Intent(getApplicationContext(),
			// EventDetail.class);
			// intent.putExtras(eventDetails);
			// intent.putExtra("isUser", true); // Matt: allow editting of event
			//
			// startActivity(intent);

		} else {
			database.updateEvents(eventId, eventProfile);
			finish();
		}
	}

	public static void setDate(Date d) {
		eventDateTime.setDate(d.getDate());
		eventDateTime.setMonth(d.getMonth() - 1);
		eventDateTime.setYear(d.getYear() - 1900);
	}

	public static void setTime(int HOUR, int MINUTE) {
		eventDateTime.setHours(HOUR);
		eventDateTime.setMinutes(MINUTE);
	}

	public static class TimePickerFrag extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		private static int HOUR;
		private static int MINUTE;

		/**
		 * Create a new instance of MyDialogFragment, providing day, month, year
		 * as arguments.
		 * @param dialog_txt 
		 */
		static TimePickerFrag newInstance(Boolean isNew, int hour, int minute, TextView dialog_txt) {
			TimePickerFrag f = new TimePickerFrag();

			Bundle args = new Bundle();
			args.putBoolean("ISNEW", false);
			args.putInt("HOUR", hour);
			args.putInt("MINUTE", minute);
			f.setArguments(args);

			return f;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			// Use the current time as the default values for the picker

			final Calendar c = Calendar.getInstance();

			if (getArguments().getBoolean("ISNEW") == false) {
				TimePickerFrag.HOUR = getArguments().getInt("HOUR");
				TimePickerFrag.MINUTE = getArguments().getInt("MINUTE");
			} else {
				HOUR = c.get(Calendar.HOUR);
				MINUTE = c.get(Calendar.MINUTE);
			}

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this,
					TimePickerFrag.HOUR, TimePickerFrag.MINUTE,
					DateFormat.is24HourFormat(getActivity()));
		}

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			System.out.println("save data: " + hourOfDay + " " + minute);
			HOUR = hourOfDay;
			MINUTE = minute;
			setTime(HOUR, MINUTE);
			
			formatDateTime();
			Log.d("EventCreate", "inside on time set");
			dialog_txt.setText(EVENT_DATE_TIME);
			
			getDialog().cancel();
		}
	}

	public static class DatePickerFrag extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		Calendar c = null;
		private static int YEAR = 0;
		private static int MONTH = 0;
		private static int DAY = 0;
		private static int DAY_OF_WEEK = 0;

		/**
		 * Create a new instance of MyDialogFragment, providing day, month, year
		 * as arguments.
		 * @param dialog_txt 
		 */
		static DatePickerFrag newInstance(Boolean isNew, int day, int month,
				int year, TextView dialog_txt) {
			DatePickerFrag f = new DatePickerFrag();

			Bundle args = new Bundle();
			args.putBoolean("ISNEW", false);
			args.putInt("DAY", day);
			args.putInt("MONTH", month);
			args.putInt("YEAR", year);
			f.setArguments(args);

			return f;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);

			// get the arguments parsed in

			if (getArguments().getBoolean("ISNEW") == false) {
				DatePickerFrag.DAY = getArguments().getInt("DAY");
				DatePickerFrag.MONTH = getArguments().getInt("MONTH") - 1;
				DatePickerFrag.YEAR = getArguments().getInt("YEAR");
			} else {
				DAY = c.get(Calendar.DAY_OF_MONTH);
				MONTH = c.get(Calendar.MONTH);
				YEAR = c.get(Calendar.YEAR);
			}

			// Use the set date as the default date in the picker
			c = Calendar.getInstance();
			c.set(DatePickerFrag.YEAR, DatePickerFrag.MONTH, DatePickerFrag.DAY);

			DAY_OF_WEEK = c.get(Calendar.DAY_OF_WEEK);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, YEAR, MONTH, DAY);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			YEAR = year;
			MONTH = month + 1; // yeah I think android default month is wrong..
			DAY = day;
			Date newDate = new Date(YEAR, MONTH, DAY);
			setDate(newDate);
			
		}
	}
}
