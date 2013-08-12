package com.example.hackathon;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.OvalShape;
import de.arvidg.exampleactionbartabs.R;

public class CircleDrawable extends Drawable {

	private Paint paint;
	static Context context;
	OvalShape drawable;
	static private boolean isMale;
	static private int[] boy_colors;
	static private int[] girl_colors;
	private int size;
	private int pixels;


	public CircleDrawable(Context context, Boolean isMale, int size, int pixels) {
		CircleDrawable.isMale = isMale;
		this.size = size;
		this.pixels = pixels;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		CircleDrawable.context = context;
		drawable = new OvalShape();
		colorMap();
		setColor();
	}

	// I should save this somewhere else, but I need to create a new class and stuff...
	private void colorMap() { 

		boy_colors = new int[] {
				context.getResources().getColor(R.color.boy_green_grey), 
				context.getResources().getColor(R.color.boy_blue_other),
				context.getResources().getColor(R.color.boy_blue_purple),
				context.getResources().getColor(R.color.boy_brown), 
				context.getResources().getColor(R.color.boy_dark_blue),
				context.getResources().getColor(R.color.boy_dark_green),
				context.getResources().getColor(R.color.boy_dark_green_other),
				context.getResources().getColor(R.color.boy_dark_grey),
				context.getResources().getColor(R.color.boy_dark_grey_other),
				context.getResources().getColor(R.color.boy_green),
				context.getResources().getColor(R.color.boy_grey),
				context.getResources().getColor(R.color.boy_grey_blue),
				context.getResources().getColor(R.color.boy_grey_other),
				context.getResources().getColor(R.color.boy_other_blue),
				context.getResources().getColor(R.color.boy_purple) };

		girl_colors = new int[] {
				context.getResources().getColor(R.color.girl_blue),
				context.getResources().getColor(R.color.girl_blue_other),
				context.getResources().getColor(R.color.girl_brown),
				context.getResources().getColor(R.color.girl_dark_blue),
				context.getResources().getColor(R.color.girl_green),
				context.getResources().getColor(R.color.girl_green_other),
				context.getResources().getColor(R.color.girl_orange),
				context.getResources().getColor(R.color.girl_orange_other),
				context.getResources().getColor(R.color.girl_pale_orange),
				context.getResources().getColor(R.color.girl_pink_other),
				context.getResources().getColor(R.color.girl_purple),
				context.getResources().getColor(R.color.girl_purple_other),
				context.getResources().getColor(R.color.girl_red),
				context.getResources().getColor(R.color.girl_yellow)};
	}

	private void setColor() {

		// set default brown
		// if the code is working properly it will get over written anyway
		int color = context.getResources().getColor(R.color.girl_brown);
		Random rand = new Random();

		// check if boy/girl
		if (isMale == true) {
			color = boy_colors[rand.nextInt(boy_colors.length)];
		} else {
			color = girl_colors[rand.nextInt(girl_colors.length)];

		}
		paint.setColor(color);
	}

	@Override
	public void draw(Canvas canvas) {

		//Merelda have to fix this crap of passing stuff around everywhere!
		canvas.drawCircle(pixels/2, pixels/2, size, paint);
	}

	@Override
	public void setAlpha(int alpha) {

	}

	@Override
	public void setColorFilter(ColorFilter cf) {

	}

	@Override
	public int getOpacity() {
		return 255;
	}

}
