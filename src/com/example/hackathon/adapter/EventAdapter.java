package com.example.hackathon.adapter;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
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
	private ArrayList<ListEvents> values;
	
	// Matt: instead of this, Matt will probably use the user object
//		String[] imageUrls = {"http://photos2.meetupstatic.com/photos/event/a/b/7/e/global_254563902.jpeg"
//					, "http://photos4.meetupstatic.com/photos/event/1/f/e/c/global_218168172.jpeg"};
//		String[] names = {"Startup Ideas", "Writing Skills"};
//		String[] info = {"Thu | 21 Jul | 3 Attending",
//				"Sat | 23 Jul | 5 Attending"};
//		String[] location = {"Bonjour Cafe, 21 4th St Linden",
//		"MamaBeans, Parkhurst"};
		DisplayImageOptions options;
		protected static ImageLoader imageLoader = ImageLoader.getInstance();

	// constructor
	public EventAdapter (Context context, ArrayList<ListEvents> events, ImageLoader imageLoader) {
		super(context, R.layout.events_adapter, events);
		this.context = context;
		this.values = events;
		EventAdapter.imageLoader = imageLoader;
		
		
	      options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.ic_launcher)
			.showImageForEmptyUri(R.drawable.ic_launcher)
			.showImageOnFail(R.drawable.ic_launcher)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.displayer(new RoundedBitmapDisplayer(20))
			.build();
	}
	 public static void initImageLoader(Context context) {
	        
	        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
	                .threadPriority(Thread.NORM_PRIORITY - 2)
	                .denyCacheImageMultipleSizesInMemory()
	                .discCacheFileNameGenerator(new Md5FileNameGenerator())
	                .tasksProcessingOrder(QueueProcessingType.LIFO)
	                .enableLogging()
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

private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

private class ViewHolder {
	public TextView name;
	public TextView info;
	public TextView location;
	public ImageView image;
}

//@Override
//public int getCount() {
//	return imageUrls.length;
//}
//
// @Override
// public Object getItem(int position) {
// return position;
// }

@Override
public long getItemId(int position) {
	return position;
}
	 
   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
	   

		View v = convertView;
		final ViewHolder holder;
       if (convertView == null) {
       	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

       	v = inflater.inflate(R.layout.events_adapter, parent,false);
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

       //Matt: change here to have name and description
       holder.name.setText(values.get(position).getName());
//       holder.info.setText(values.get(position).get);
       holder.location.setText(values.get(position).getLocation());
       imageLoader.displayImage(values.get(position).getEventPicture(), holder.image, options, null);

       return v;
//		LayoutInflater inflater = (LayoutInflater) context
//			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//		View rowView = inflater.inflate(R.layout.events_adapter, parent, false);
//		TextView textName = (TextView) rowView.findViewById(R.id.event_name);
//		TextView textInfo = (TextView) rowView.findViewById(R.id.event_info);
//		TextView textLocation = (TextView) rowView.findViewById(R.id.event_location);
//		ImageView imageView = (ImageView) rowView.findViewById(R.id.event_pic);
//		textName.setText(values.get(position).getName());
//		textInfo.setText(values.get(position).getSkill());
//		textLocation.setText(values.get(position).getLocation());
//		
//
//		// how to change image stuff
////		imageView.setImageResource(R.drawable.android_logo);
//
//		return rowView;
	}

}