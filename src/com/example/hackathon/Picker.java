package com.example.hackathon;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import de.arvidg.exampleactionbartabs.R;
import android.view.View.OnClickListener;

public class Picker extends DialogFragment {

	 DialogFragment timePicker;
	DialogFragment datePicker;
	static TextView dialog_date;
	static TextView dialog_time;
	static int HOUR_OF_DAY;
	static int MINUTES;
	static int YEAR;
	static int MONTH;
	static int DAY;
	public static TextView txt;
	private Button saveBtn;
	static String results;
	
	public Picker() {
		timePicker = new TimePickerFragment();
		datePicker = new DatePickerFragment();
		results = null;
	}
	

//	public Picker(TextView txt) {
//		// Empty constructor required for DialogFragment
//		timePicker = new TimePickerFragment();
//		datePicker = new DatePickerFragment();
//		this.txt = txt;
//		addListener();
//		}

//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		View view = inflater.inflate(R.layout.picker, container);
//		getDialog().setTitle("Event Date and Time");
//		dialog_date = (TextView) view.findViewById(R.id.dialog_date);
//		dialog_time = (TextView) view.findViewById(R.id.dialog_time);
//
//		timePicker = new TimePickerFragment();
//		timePicker.show(getFragmentManager(), "TimePicker");
//		dialog_time.setText(HOUR_OF_DAY + " : " + MINUTES);
//		datePicker = new DatePickerFragment();
//		datePicker.show(getFragmentManager(), "DatePicker");
//		dialog_date.setText(DAY + " / " + MONTH + " / " + YEAR);
//
//		// didn't save properly
//		// when show dialog only the first one is shown
//		// I think because you show dialog first
//		// rather screw this and just print the results in the EventDetil
//		// Page....
//
//		saveBtn = (Button) view.findViewById(R.id.pickerSaveBtn);
//		addListener();
//
//		return view;
//	}

//	@Override
//	public void onCancel(DialogInterface dialog) {
//		// TODO Auto-generated method stub
//
//		HOUR_OF_DAY = 0;
//		MINUTES = 0;
//		YEAR = 0;
//		MONTH = 0;
//		DAY = 0;
//		((DialogInterface) timePicker).cancel();
//		((DialogInterface) datePicker).cancel();
//		super.onCancel(dialog);
//
//	}

//	public void addListener() {
//
//		saveBtn.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				// do something
//				System.out.println("I'm inside On Click???");
//				// SaveData(view);
////				getDialog().cancel(); // supposed to call save data?
//				
//			}
//		});
//	}

	public String getResults() {
		String results = Integer.toString(DAY) + "/" + Integer.toString(MONTH)
				+ "/" + Integer.toString(YEAR) + " | "
				+ Integer.toString(HOUR_OF_DAY) + ":"
				+ Integer.toString(MINUTES);

		System.out.println("results in getresults: " + results);
		return results;
	}

	// public void SaveData(View v){
	// //Mel: still have to save data here
	// dialog_date = (TextView) v.findViewById(R.id.dialog_date);
	// dialog_time = (TextView) v.findViewById(R.id.dialog_time);
	// dialog_date.setText(DAY + " " + MONTH + " " + YEAR);
	// dialog_time.setText(HOUR_OF_DAY + " : " + MINUTES );
	// System.out.println("Are you coming here to save?");
	// //MEL: supposed to return to the caller activity to display...
	// }

	@SuppressLint("ValidFragment")
	public static class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker

			System.out.println("And here?");

			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// datePicker = new DatePickerFragment();
			// datePicker.show(getFragmentManager(), "DatePicker");
			// Mel: still have to save data here
			System.out.println("save data: " + hourOfDay + " " + minute);
			HOUR_OF_DAY = hourOfDay;
			MINUTES = minute;
//			System.out.println("verify data time: " + HOUR_OF_DAY + " "
//					+ MINUTES);
//			System.out.println("verify data date: " + YEAR + " " + MONTH + " "
//					+ DAY);
//			results = results + Integer.toString(HOUR_OF_DAY) + " : " + Integer.toString(MINUTES);
			getDialog().cancel();
		}
	}

	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			// Mel: still have to save data here
			YEAR = year;
			MONTH = month + 1; // yeah I think android default month is wrong..
			DAY = day;
//			results = Integer.toString(YEAR)+ "/" + Integer.toString(MONTH)+ "/" +  Integer.toString(DAY);
		}
	}

}
