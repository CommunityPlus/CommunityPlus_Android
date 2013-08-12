package com.example.hackathon;

import de.arvidg.exampleactionbartabs.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditProfile extends Activity{
	protected SharedMenu sharedMenu;
	
	// stuff that needs to be saved
	private String USER_NAME;
	private String USER_DESCRIPTION;
	private String USER_WORD1;
	private String USER_WORD2;
	private String USER_WORD4;
	private String USER_WORD5;
	private String USER_WORD6;
	private String USER_WORD7;
	
	// general stuff
	static TextView txtView = null;
	static Button SaveBtn = null;
	static TextView dialog_txt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_edit);
		sharedMenu = new SharedMenu();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (sharedMenu.onOptionsItemSelected(item, this) == false) {
			// handle local menu items here or leave blank
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public void EditFields(View v) {

		txtView = null;
		txtView= (TextView) findViewById(v.getId());

		// get prompts.xml view
		LayoutInflater li = LayoutInflater.from(EditProfile.this);
		View dialogBox = li.inflate(R.layout.dialog_box, null);
		

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(
				EditProfile.this, R.style.AlertDialogCustom));
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
	
	public void SaveEdit(View v) {
		
		Toast.makeText(getApplicationContext(), "Detail saved", Toast.LENGTH_SHORT).show();
		txtView = null;	//just to clear, doesn't really need to 
	    
	    // super inefficient but whatever
	    txtView = (TextView) findViewById(R.id.nameTextView);
	    USER_NAME = txtView.getText().toString();
	    txtView = (TextView) findViewById(R.id.descriptionTextView);
		USER_DESCRIPTION = (String) txtView.getText().toString();
		txtView = (TextView) findViewById(R.id.Word1);
		USER_WORD1 = (String) txtView.getText().toString();
		txtView = (TextView) findViewById(R.id.Word2);
		USER_WORD2 = (String) txtView.getText().toString();
		txtView = (TextView) findViewById(R.id.Word3);
		USER_WORD1 = (String) txtView.getText().toString();
		txtView = (TextView) findViewById(R.id.Word4);
		USER_WORD4 = (String) txtView.getText().toString();
		txtView = (TextView) findViewById(R.id.Word5);
		USER_WORD5 = (String) txtView.getText().toString();
		txtView = (TextView) findViewById(R.id.Word6);
		USER_WORD6 = (String) txtView.getText().toString();
		txtView = (TextView) findViewById(R.id.Word7);
		USER_WORD7 = (String) txtView.getText().toString();

	    // Matt has to save event here
		
		// Go back to the PeopleDetail page
		Intent i = new Intent(EditProfile.this, PeopleDetail.class);
		i.putExtra("isUser", true);
		startActivity(i);
	    
	}

}
