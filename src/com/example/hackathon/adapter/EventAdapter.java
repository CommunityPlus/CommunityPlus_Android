package com.example.hackathon.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hackathon.models.ListEvents;
import com.example.hackathon.models.ListUsers;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import de.arvidg.exampleactionbartabs.R;

public class EventAdapter extends ArrayAdapter<ListEvents> {

	private Context context;
	private ArrayList<ListEvents> events;

	DisplayImageOptions options;
	protected static ImageLoader imageLoader = ImageLoader.getInstance();
	
	Date date;
	private Date eventDateTime;
	String eventDT;

	// constructor
	public EventAdapter(Context context, ArrayList<ListEvents> events,
			ImageLoader imageLoader) {
		super(context, R.layout.events_adapter, events);
		this.context = context;
		this.events = events;
		EventAdapter.imageLoader = imageLoader;

		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(20))
				.build();
	}
	
	private void getDateTime(int pos) {
		String DateTime = events.get(pos).getEventDate() + " " + events.get(pos).getEventTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			eventDateTime = format.parse(DateTime);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
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
	
	private String eventInfo(int pos){
		int year = eventDateTime.getYear() + 1900;
		int month = eventDateTime.getMonth() + 1;

		String day = getDayOfWeek(eventDateTime.getDay());

		eventDT = day + "  |  " + eventDateTime.getDate() + " / "
				+ month + " / " + year + "  |  " + eventDateTime.getHours()
				+ " : " + eventDateTime.getMinutes();
		
		return eventDT;
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

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	// private ImageLoadingListener animateFirstListener = new
	// AnimateFirstDisplayListener();

	private class ViewHolder {
		public TextView name;
		public TextView info;
		public TextView location;
		public ImageView image;
	}

	// @Override
	// public int getCount() {
	// return imageUrls.length;
	// }
	//
	// @Override
	// public Object getItem(int position) {
	// return position;
	// }

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void formatInfo(int position){
		// format: Thu | 21 Aug | 3 Attending
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		final ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			v = inflater.inflate(R.layout.events_adapter, parent, false);
			holder = new ViewHolder();
			holder.name = (TextView) v.findViewById(R.id.event_name);
			holder.info = (TextView) v.findViewById(R.id.event_info);
			holder.image = (ImageView) v.findViewById(R.id.event_pic);
			holder.location = (TextView) v.findViewById(R.id.event_location);
			holder.info.setMaxLines(1);
			holder.location.setMaxLines(1);
			holder.name.setMaxLines(1);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		// Matt: change here to have name and description
		holder.name.setText(events.get(position).getName());
		
		getDateTime(position);
		holder.info.setText(eventInfo(position));
		
		holder.location.setText(events.get(position).getLocation());
		imageLoader.displayImage(events.get(position).getEventPicture(),
				holder.image, options, null);

		return v;
		// LayoutInflater inflater = (LayoutInflater) context
		// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//
		// View rowView = inflater.inflate(R.layout.events_adapter, parent,
		// false);
		// TextView textName = (TextView) rowView.findViewById(R.id.event_name);
		// TextView textInfo = (TextView) rowView.findViewById(R.id.event_info);
		// TextView textLocation = (TextView)
		// rowView.findViewById(R.id.event_location);
		// ImageView imageView = (ImageView)
		// rowView.findViewById(R.id.event_pic);
		// textName.setText(events.get(position).getName());
		// textInfo.setText(events.get(position).getSkill());
		// textLocation.setText(events.get(position).getLocation());
		//
		//
		// // how to change image stuff
		// // imageView.setImageResource(R.drawable.android_logo);
		//
		// return rowView;
	}
	
	public void Close() {
		onDestroy();
		System.out.println("does it come to close in People adapter??");
	}

	private void onDestroy() {
		// TODO Auto-generated method stub
		System.out.println("does it come to onDestroy in Poeple adapter??");
		imageLoader.destroy();
	}

	private void onStop() {
		// TODO Auto-generated method stub
		imageLoader.destroy();
	}
}