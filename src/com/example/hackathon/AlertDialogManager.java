package com.example.hackathon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import de.arvidg.exampleactionbartabs.R;

public class AlertDialogManager {
	/**
	 * Function to display simple Alert Dialog I think the edit dialog box in
	 * EventDetail can be merged here Or something similar can be done
	 * */

	public static Context appContext;
	public static RadioButton radioButton;
	public AlertDialog alertDialog;
	LayoutInflater li;
	View dialogBox;
	RadioGroup radioGroup;

	public void showAlertDialog(Context context, String title, String message,
			Boolean status) {

		// get prompts.xml view
		li = LayoutInflater.from(context);
		dialogBox = li.inflate(R.layout.rsvp_type, null);

		alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setView(dialogBox);

		radioGroup = (RadioGroup) dialogBox.findViewById(R.id.radio_rsvp);
		;
		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// do something here?
				ListView lw = ((AlertDialog) dialog).getListView();
				Object checkedItem = lw.getAdapter().getItem(
						lw.getCheckedItemPosition());
				
				Log.d("AlertDialogManager", "checkeditem: " + checkedItem);
			}
		});

		// get selected radio button from radioGroup
		int selectedId = radioGroup.getCheckedRadioButtonId();

		// find the radiobutton by returned id
		radioButton = (RadioButton) dialogBox.findViewById(selectedId);

		// Toast.makeText(appContext, radioButton.getText(),
		// Toast.LENGTH_SHORT).show();
		// System.out.println("fail here 1=is 8");

		// Showing Alert Message
		alertDialog.show();
	}

}