/*
   Copyright 2012 Ruben Serrano

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package org.uab.deic.uabdroid.solutions.unit3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.DatePicker;
import android.widget.EditText;

public class FormAppActivity extends FragmentActivity 
{	
	// Static fields for the preferences key names
	public static final String STATE_NOT_SAVED = "STATE_NOT_SAVED";
	public static final String FORM_FIELD_NAME = "FORM_FIELD_NAME";
	public static final String FORM_FIELD_DEVELOPER = "FORM_FIELD_DEVELOPER";
	public static final String FORM_FIELD_DAY = "FORM_FIELD_DAY";
	public static final String FORM_FIELD_MONTH = "FORM_FIELD_MONTH";
	public static final String FORM_FIELD_YEAR = "FORM_FIELD_YEAR";
	public static final String FORM_FIELD_URL = "FORM_FIELD_URL";
	
	private boolean mButtonPressed = false; 

	@Override
	protected void onCreate(Bundle _savedInstanceState) 
	{
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.form_activity_layout);
		
		mButtonPressed = false;
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		FormFragment fragment = (FormFragment) fragmentManager.findFragmentById(R.id.framelayout_form_activity);
	
		if (fragment == null)
		{
			fragment = new FormFragment();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
			fragmentTransaction.replace(R.id.framelayout_form_activity, fragment);
			fragmentTransaction.commit();
		}
	}

	@Override
	public void onBackPressed() 
	{
		// If the back button is pressed by error, store the state of the form
		storeCurrentState();
		
		super.onBackPressed();
	}

	@Override
	protected void onStop() 
	{
		// If the onStop event is reached and is not caused by the buttons
		// Accept or Cancel, store the state of the form
		if (!mButtonPressed)
		{
			// If the activity is finished by the system, we previously
			// stored the current state
			storeCurrentState();
		}
		
		super.onStop();
	}
	
	// As the buttons are created in the fragment, the buttons's listeners
	// should be instantiated there, and so we have to call the setter from
	// there
	public void setButtonPressed() 
	{
		mButtonPressed = true;
	}
	
	// Method for storing the state of the form in case the activity
	// is closed before we actually decide to intentionally close it
	private void storeCurrentState()
	{
		// As the fragment is loaded dynamically, we souldn't suppose that it's going to be
		// the FormFragment. In this case where we don't have any other, we could avoid
		// the conditional, but it's a good practice either way
		EditText editText = (EditText) findViewById(R.id.edittext_form_name);
		
		if( editText != null)
		{		
			// Obtain a reference to the preferences
			SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
			// And then a reference to the editor to store the preferences
			SharedPreferences.Editor editor = preferences.edit();
			
			// Make clear that the state of the form is not saved
			editor.putBoolean(STATE_NOT_SAVED,true);
						
			// Store the values of the form
			editor.putString(FORM_FIELD_NAME, editText.getText().toString());
			
			editText = (EditText) findViewById(R.id.edittext_form_developer);
			editor.putString(FORM_FIELD_DEVELOPER, editText.getText().toString());
			
			DatePicker datePicker = (DatePicker) findViewById(R.id.datepicker_form_date); 
			editor.putInt(FORM_FIELD_DAY, datePicker.getDayOfMonth());
			editor.putInt(FORM_FIELD_MONTH, datePicker.getMonth());
			editor.putInt(FORM_FIELD_YEAR, datePicker.getYear());
			
			editText = (EditText) findViewById(R.id.edittext_form_url);
			editor.putString(FORM_FIELD_URL, editText.getText().toString());
			
			// In order to really store the values, we must perform a commit() operation
			editor.commit();
		}
	}
}
