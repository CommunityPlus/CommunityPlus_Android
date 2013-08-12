package com.example.hackathon.adapter;

import java.util.ArrayList;
import java.util.Collections;
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

public class PeopleAdapter extends ArrayAdapter<ListUsers> {

	private Context context;
	private ArrayList<ListUsers> values;

	DisplayImageOptions options;
	protected static ImageLoader imageLoader;// = ImageLoader.getInstance();

	// constructor
	public PeopleAdapter(Context context, ArrayList<ListUsers> user, ImageLoader imageLoader) {
		super(context, R.layout.people_adapter, user);
		this.context = context;
		this.values = user;
		
		PeopleAdapter.imageLoader = imageLoader;
		
	      options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.ic_launcher)
			.showImageForEmptyUri(R.drawable.ic_launcher)
			.showImageOnFail(R.drawable.ic_launcher)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.displayer(new RoundedBitmapDisplayer(20))
			.build();
	}


	private class ViewHolder {
		public TextView name;
		public TextView skill;
		public ImageView image;
	}

//	@Override
//	public int getCount() {
//		return imageUrls.length;
//	}

//	 @Override
//	 public Object getItem(int position) {
//	 return position;
//	 }

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override //added "final" for int position
	public View getView(final int position, View convertView, ViewGroup parent) {

		View v = convertView;
		final ViewHolder holder;
        if (convertView == null) {
        	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        	v = inflater.inflate(R.layout.people_adapter, parent,false);
            holder = new ViewHolder();
            holder.name = (TextView) v.findViewById(R.id.user_name);
            holder.skill = (TextView) v.findViewById(R.id.user_info);
            holder.image = (ImageView) v.findViewById(R.id.user_pic);
            holder.skill.setMaxLines(2);	//maximum of 2 lines
            v.setTag(holder);
            
        } else {
            holder = (ViewHolder) v.getTag();
        }

        //Matt: change here to have name and description
        holder.name.setText(values.get(position).getUserName());
        holder.skill.setText(values.get(position).getSkillOffer());	//skills[position]
        imageLoader.displayImage(values.get(position).getUserPicture(), holder.image, options, null);

        return v;
    }

	public void Close(){
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