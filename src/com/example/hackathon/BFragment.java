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

import com.example.hackathon.adapter.EventAdapter;
import com.example.hackathon.adapter.PeopleAdapter;
import com.example.hackathon.api.Database;
import com.example.hackathon.models.ListEvents;
import com.example.hackathon.models.ListUsers;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class BFragment extends ListFragment {

	private static ImageLoader imageLoader;

	// Database
	Database database;
	ListUsers listUsers;

	EventAdapter eventAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		imageLoader = ImageLoader.getInstance();
		initImageLoader(getActivity());

		// Database Stuff
		database = new Database("http://192.168.1.109:3000/api/", "android",
				"1234");
		try {
			ArrayList<ListEvents> events = database.showListEvents();
			eventAdapter = new EventAdapter(getActivity(), events, imageLoader);
		} catch (InterruptedException e) {
			Log.e("Database", "Throw InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			Log.e("Database", "Throw ExecutionException");
			e.printStackTrace();
		}

		setListAdapter(eventAdapter);
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

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		ListEvents eventSelected = (ListEvents) getListView()
				.getItemAtPosition(position);
		Toast.makeText(getActivity(),
				"You selected: " + eventSelected.getName(), Toast.LENGTH_SHORT)
				.show();
		Bundle eventDetails = new Bundle();
		eventDetails.putInt("eventId", eventSelected.getEventId());
		Log.d("BFragment", "Bfrag event id is: " + eventSelected.getEventId());

		// if (eventSelected.get)
		// boolean editable = true;
		 

		Intent intent = new Intent(getActivity().getApplicationContext(),
				EventDetail.class);
		intent.putExtras(eventDetails);
		intent.putExtra("isUser", true);	// Matt: allow editting of event
		
		startActivity(intent);
	}

	public void startAdapter(ArrayList<ListEvents> events) {

		// setListAdapter(new EventAdapter(getActivity(), events));
	}

}
