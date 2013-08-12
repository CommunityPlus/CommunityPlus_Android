package com.example.hackathon;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hackathon.api.Database;
import com.example.hackathon.models.UserProfile;
import com.google.common.primitives.Ints;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import de.arvidg.exampleactionbartabs.R;

public class PeopleDetail extends Activity {

	protected SharedMenu sharedMenu;
	boolean isUser;
	protected SessionManager sessionManager;
	private TextView txt;
	Bundle userDetails;
	// private static Boolean isMale;
	private int size;

	private int rangeS;
	private int rangeM;
	private int rangeL;

	// change pixel to dp
	final float scale = 0.0f;
	int pixels;

	// profile pic
	static ImageLoader imageLoader;
	DisplayImageOptions options;

	// description dialog
	private Dialog description_dialog;

	// UserProfile
	Database database;
	UserProfile userProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userDetails = getIntent().getExtras();

		// Create new UserProfile based on userId (From DB)
		database = new Database("http://communityplus.herokuapp.com/api/", "android",
				"1234");

		try {
			userProfile = database
					.showUserProfile(userDetails.getInt("userId"));
		} catch (InterruptedException e) {
			Log.e("database", "Throw InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			Log.e("database", "Throw ExecuteException");
			e.printStackTrace();
		}

		// isMale = userDetails.getBoolean("isMale");
		if (userProfile.isMale() == true)
			setContentView(R.layout.user_detail_male);
		else
			setContentView(R.layout.user_detail);

//		isUser = userDetails.getBoolean("isUser");
		sharedMenu = new SharedMenu();

		// set up stuff for dynamically draw skills circle
		float scale = getApplicationContext().getResources()
				.getDisplayMetrics().density;
		pixels = (int) (107.0f * scale + 0.5f);

		// profile pic
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher)
				.resetViewBeforeLoading(true).cacheOnDisc(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new RoundedBitmapDisplayer(110)).build();

		imageLoader = ImageLoader.getInstance();
		initImageLoader(getApplicationContext());

		populareUserDetail();
		drawSkillsCircle();
		setProfilePic();
	}

	public static void initImageLoader(Context context) {

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging()
				.build();

		ImageLoader.getInstance().init(config);
	}

	private void populareUserDetail() {
		// String[] wordCloud = userDetails.getStringArray("wordCloud");

		txt = (TextView) findViewById(R.id.nameTextView);
		txt.setText(userProfile.getUserName());
		txt = (TextView) findViewById(R.id.descriptionTextView);
		txt.setText(userProfile.getLocation());

		// Skills
		txt = (TextView) findViewById(R.id.skill1_text);
		txt.setText(userProfile.getSkillsLearn().get(0).getName());
		txt = (TextView) findViewById(R.id.skill2_text);
		txt.setText(userProfile.getSkillsLearn().get(1).getName());
		txt = (TextView) findViewById(R.id.skill3_text);
		txt.setText(userProfile.getSkillsLearn().get(2).getName());
		txt = (TextView) findViewById(R.id.skill4_text);
		txt.setText(userProfile.getSkillsOffer().get(0).getName());
		txt = (TextView) findViewById(R.id.skill5_text);
		txt.setText(userProfile.getSkillsOffer().get(1).getName());
		txt = (TextView) findViewById(R.id.skill6_text);
		txt.setText(userProfile.getSkillsOffer().get(2).getName());

		txt = (TextView) findViewById(R.id.Word1);
		txt.setText(userProfile.getWordCloud()[0]);
		txt = (TextView) findViewById(R.id.Word2);
		txt.setText(userProfile.getWordCloud()[1]);
		txt = (TextView) findViewById(R.id.Word3);
		txt.setText(userProfile.getWordCloud()[2]);
		txt = (TextView) findViewById(R.id.Word4);
		txt.setText(userProfile.getWordCloud()[3]);
		txt = (TextView) findViewById(R.id.Word5);
		txt.setText(userProfile.getWordCloud()[4]);
		txt = (TextView) findViewById(R.id.Word6);
		txt.setText(userProfile.getWordCloud()[5]);
		txt = (TextView) findViewById(R.id.Word7);
		txt.setText(userProfile.getWordCloud()[6]);
	}

	private void draw(TextView[] skills) {

		for (int i = 0; i < skills.length; i++) {
			if (Integer.parseInt(skills[i].getText().toString()) <= rangeS) {
				size = 77;
			} else if (Integer.parseInt(skills[i].getText().toString()) > rangeS
					&& Integer.parseInt(skills[i].getText().toString()) <= rangeM) {
				size = 87;
			} else if (Integer.parseInt(skills[i].getText().toString()) > rangeM
					&& Integer.parseInt(skills[i].getText().toString()) <= rangeL) {
				size = 97;
			} else if (Integer.parseInt(skills[i].getText().toString()) > rangeL) {
				size = 107;
			} else {
				Log.e("PeopleDetail", "Bad Conditional Formatting");
				size = 97; // if it comes here then something is wrong with my
							// conditional statement
			}
			skills[i].getText();
			skills[i].setWidth(pixels);
			skills[i].setHeight(pixels);
			skills[i].setBackground(new CircleDrawable(getApplicationContext(),
					userProfile.isMale(), size, pixels));
		}
	}

	private void findWeighting(TextView[] skills) {
		int min = 0;
		int max = 0;

		int[] myArray = { Integer.parseInt(skills[0].getText().toString()),
				Integer.parseInt(skills[1].getText().toString()),
				Integer.parseInt(skills[2].getText().toString()),
				Integer.parseInt(skills[3].getText().toString()),
				Integer.parseInt(skills[4].getText().toString()),
				Integer.parseInt(skills[5].getText().toString()) };

		min = Ints.min(myArray);
		max = Ints.max(myArray);

		// scale
		rangeS = (max - min) / 3;
		rangeM = 2 * (max - min) / 3;
		rangeL = max - min;
	}

	/*
	 * Populates array with the TextView Find the weighting of each circle
	 * depending on the number of events associated to it Draw the circle with
	 * the weighting
	 */
	private void drawSkillsCircle() {
		TextView[] array = { (TextView) findViewById(R.id.skill1),
				(TextView) findViewById(R.id.skill2),
				(TextView) findViewById(R.id.skill3),
				(TextView) findViewById(R.id.skill4),
				(TextView) findViewById(R.id.skill5),
				(TextView) findViewById(R.id.skill6) };
		findWeighting(array);
		draw(array);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		SessionManager session = new SessionManager(getApplicationContext());
		
		
		if (Integer.parseInt(session.getUserDetails().get("id")) == userDetails.getInt("userId")) {
			isUser = true;
			inflater.inflate(R.menu.edit, menu);
		} else {
			isUser = false;
			inflater.inflate(R.menu.main, menu);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (sharedMenu.onOptionsItemSelected(item, this) == false) {
			// handle local menu items here or leave blank
		}
		return super.onOptionsItemSelected(item);
	}

	private void setProfilePic() {
		ImageView imageView = (ImageView) findViewById(R.id.profilePictureImageView);

		imageLoader.displayImage(userProfile.getUserPicture(), imageView,
				options, new SimpleImageLoadingListener());
	}

	public void DescriptionExpand(View v) {

		description_dialog = new Dialog(PeopleDetail.this);
		description_dialog.getWindow();
		description_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		if (userProfile.isMale() == true)
			description_dialog.setContentView(R.layout.user_description_boy);
		else
			description_dialog.setContentView(R.layout.user_description);

		description_dialog.setCancelable(true);
		description_dialog.setCanceledOnTouchOutside(true);

		description_dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		description_dialog.show();

		Button close = (Button) description_dialog
				.findViewById(R.id.button_close);

		close.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				System.out.println("close close close");
				description_dialog.dismiss();
			}
		});

	}
}