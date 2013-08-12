package com.example.hackathon;

import java.util.concurrent.ExecutionException;

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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import de.arvidg.exampleactionbartabs.R;

public class EventCreate extends Activity { //FragmentActivity
	
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
	private String EVENT_NAME= null;
	private String EVENT_LOCATION = null;
	private String EVENT_DATE_TIME = null;
	private String EVENT_DESCRIPTION = null;
	protected SharedMenu sharedMenu;
	
	// if it's an existing event
	static ImageLoader imageLoader;
	DisplayImageOptions options;
	EventProfile eventProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_create);
		
		Bundle eventDetails = getIntent().getExtras();
		
		Database database = new Database("http://192.168.1.109:3000/api/", "android",
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
		
		
		//brand new event has eventId -1
		//check if it is an existing event
		Log.d("EventCreate", "event id in event create is: " + eventDetails.getInt("eventId"));
		if (eventDetails.getInt("eventId") > 0){

			try {
				
				int event_id = eventDetails.getInt("eventId");
				Log.d("EventCreate", "event id in event create is: " + event_id);
				eventProfile = database.showEventProfile(event_id);
				
				this.EVENT_ORGANIZER = eventProfile.getHostName();
				this.EVENT_NAME = eventProfile.getName();
				this.EVENT_DESCRIPTION = eventProfile.getDescription();
				this.EVENT_DATE_TIME = eventProfile.getDateTime();
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

		}
		
		descriptionText = (TextView) findViewById(R.id.event_description);
		ic_expand = (ImageView) findViewById(R.id.ic_expand);
		ic_expand.setBackgroundResource(R.drawable.ic_expand);
		
		 sharedMenu = new SharedMenu();
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

	private void populateEventDetail() {
		TextView txt = (TextView) findViewById(R.id.event_name);
		txt.setText(eventProfile.getName());
		txt = (TextView) findViewById(R.id.event_location);
		txt.setText(eventProfile.getLocation());
		txt = (TextView) findViewById(R.id.event_description);
		txt.setText(eventProfile.getDescription());

		// Find # of attendees, date, time
		txt = (TextView) findViewById(R.id.event_date_time);
		txt.setText("Sun" + " | " + "21 Jul" + " | "
				+ Integer.toString(eventProfile.getAttendees().size())
				+ "  Attending");

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
        inflater.inflate(R.menu.normal, menu);		// don't know why main doesn't work anymore
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	    if(sharedMenu.onOptionsItemSelected(item, this) == false) {
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
			ic_expand.setBackgroundResource(R.drawable.ic_stat_collapse); //ic_stat_collapse

		} else {
			descriptionText.setMaxLines(2);
			IsExpand = false;
			ic_expand.setBackgroundResource(R.drawable.ic_expand);
			System.out.println("COme inside expand ELSE");
		}

	}


	public void EditFields(View v) {

		txtView = null;
		txtView= (TextView) findViewById(v.getId());

		// get prompts.xml view
		LayoutInflater li = LayoutInflater.from(EventCreate.this);
		View dialogBox = li.inflate(R.layout.dialog_box, null);
		

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(
				EventCreate.this, R.style.AlertDialogCustom));
//		alertDialogBuilder.setTitle("Edit " + v.getContentDescription());
		dialog_txt = (TextView) dialogBox.findViewById(R.id.dialog_title);
		dialog_txt.setText(v.getContentDescription());


		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(dialogBox);
		final EditText userInput = (EditText) dialogBox.findViewById(R.id.user_input);
//		title = (EditText) dialogBox.findViewById(R.id.dialog_title);
		
		if (txtView == null){
			userInput.setText(userInput.getText());
			}
			else{
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
		
		System.out.println("come here1");
		dialog_txt = (TextView) findViewById(R.id.event_date_time);
	    
//	    Picker newFragment = new Picker(title); //Picker = detailFragment
	    Picker newFragment = new Picker(); 
	    newFragment.timePicker.show(getFragmentManager(), "TimePicker");
	    newFragment.datePicker.show(getFragmentManager(), "DatePicker");
	    String results = newFragment.getResults();

	    dialog_txt.setText(results);
	    System.out.println("come here 5");
	    dialog_txt.setText(newFragment.getResults());
	}
	
	public void SaveEvent(View v) {
		
		txtView = null;	//just to clear, doesn't really need to 
		Toast.makeText(getApplicationContext(), "Event saved", Toast.LENGTH_SHORT).show();
	    
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
	
	    // Matt has to save event here
	    
	}
}
