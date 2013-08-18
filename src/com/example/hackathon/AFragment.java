package com.example.hackathon;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hackathon.adapter.PeopleAdapter;
import com.example.hackathon.api.Database;
import com.example.hackathon.models.ListUsers;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class AFragment extends ListFragment {

	PeopleAdapter peopleAdapter;
	static ImageLoader imageLoader;

	// Database
	Database database;
	ListUsers listUsers;
	
	protected SharedMenu sharedMenu;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		imageLoader = ImageLoader.getInstance();
		initImageLoader(getActivity());

		// Database Stuff
		database = new Database("http://192.168.1.109:3000/api/", "android",
				"1234");
		try {
			ArrayList<ListUsers> users = database.showListUsers();
			peopleAdapter = new PeopleAdapter(getActivity(), users, imageLoader);
			setListAdapter(peopleAdapter);
		} catch (InterruptedException e) {
			Log.e("Database", "Throw InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			Log.e("Database", "Throw ExecutionException");
			e.printStackTrace();
		}
		
		sharedMenu = new SharedMenu();

		
	}
//	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
// 	   
//		if(sharedMenu.onOptionsItemSelected(item, this) == false) {
//		      // handle local menu items here or leave blank
////				Intent i = new Intent(StartActivity.this, PeopleDetail.class);
////				Bundle b = null;
////				Log.d("SharedMenu", "user id in shared menu is: " + session.getUserDetails().get("id"));
////				b.putInt("userId", Integer.parseInt(session.getUserDetails().get("id")));
//////				i.putExtra("userId", session.getUserDetails().get("id"));
////				i.putExtras(b);
////				startActivity(i);
//
//		    }
//		    return super.onOptionsItemSelected(item);
//	}
//	
//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		
//        inflater.inflate(R.menu.main, menu);	
//	   
//		if(sharedMenu.onOptionsItemSelected(item, this) == false) {
//		      // handle local menu items here or leave blank
////				Intent i = new Intent(StartActivity.this, PeopleDetail.class);
////				Bundle b = null;
////				Log.d("SharedMenu", "user id in shared menu is: " + session.getUserDetails().get("id"));
////				b.putInt("userId", Integer.parseInt(session.getUserDetails().get("id")));
//////				i.putExtra("userId", session.getUserDetails().get("id"));
////				i.putExtras(b);
////				startActivity(i);
//
//		    }
//		    return super.onOptionsItemSelected(item);
//	}

	public static void initImageLoader(Context context) {

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging()
				.build();

		ImageLoader.getInstance().init(config);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		// NB: This --> getActivity().getApplicationContext() <-- is the life
		// saver
		ListUsers userSelected = (ListUsers) getListView().getItemAtPosition(
				position);
		 Toast.makeText(getActivity(), "You selected: " +
		 userSelected.getUserName(), Toast.LENGTH_SHORT).show();
		Bundle userDetails = new Bundle();
		userDetails.putInt("userId", userSelected.getUserID());

		Intent intent = new Intent(getActivity().getApplicationContext(),
				PeopleDetail.class);
		intent.putExtras(userDetails);
		startActivity(intent);
		// finish();
	}
}
