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

import java.util.Calendar;

import org.uab.deic.uabdroid.solutions.unit3.entities.AppData;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class FormFragment extends Fragment 
{
	private EditText mEditTextName;
	private EditText mEditTextDeveloper;
	private DatePicker mDatePickerDate;
	private EditText mEditTextURL;
	
	@Override
	public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) 
	{
		View view = _inflater.inflate(R.layout.form_layout, _container, false);
		return view;
	}

	// We override onActivityCreated in order to restore the state of the fields if needed
	@Override
	public void onActivityCreated(Bundle _savedInstanceState) 
	{
		super.onActivityCreated(_savedInstanceState);
		
		// As we are in a fragment, we need the parent activity instance in order to have access 
		// to the activity's SharedPreferences and the layout Views
		final Activity parentActivity = getActivity();
		
		SharedPreferences activityPreferences = parentActivity.getPreferences(Context.MODE_PRIVATE);
		
		mEditTextName = (EditText)parentActivity.findViewById(R.id.edittext_form_name);
		mEditTextDeveloper = (EditText)parentActivity.findViewById(R.id.edittext_form_developer);
		mDatePickerDate = (DatePicker)parentActivity.findViewById(R.id.datepicker_form_date);
		mEditTextURL = (EditText)parentActivity.findViewById(R.id.edittext_form_url);
		
		// if the last run of the activity wasn't finished using the Accept or Cancel buttons
		// then we must restore the state
		if (activityPreferences.getBoolean(FormAppActivity.STATE_NOT_SAVED, false))
		{
			// Restore the name
			mEditTextName.setText(activityPreferences.getString(FormAppActivity.FORM_FIELD_NAME, ""));
			
			// Restore the developer name
			mEditTextDeveloper.setText(activityPreferences.getString(FormAppActivity.FORM_FIELD_DEVELOPER, ""));
			
			// Restore the calendar. The default values are extracted from Calendar, which is 
			// instantiated with the current date
			Calendar calendar = Calendar.getInstance();
			int day = activityPreferences.getInt(FormAppActivity.FORM_FIELD_DAY, calendar.get(Calendar.DAY_OF_MONTH));
			int month = activityPreferences.getInt(FormAppActivity.FORM_FIELD_MONTH, calendar.get(Calendar.MONTH)+1);
			int year = activityPreferences.getInt(FormAppActivity.FORM_FIELD_YEAR, calendar.get(Calendar.YEAR));
			
			mDatePickerDate.updateDate(year, month, day);
			
			// Restore the URL
			mEditTextURL.setText(activityPreferences.getString(FormAppActivity.FORM_FIELD_URL, ""));
			
			// Finally, we restore the state of STATE_NOT_SAVED to false
			SharedPreferences.Editor editor = activityPreferences.edit();
			editor.putBoolean(FormAppActivity.STATE_NOT_SAVED, false);
			editor.commit();
		}
		
		Button cleanButton = (Button) parentActivity.findViewById(R.id.button_form_clean);
		cleanButton.setOnClickListener(new OnClickListener()
		{
			// When the button is clicked, we erase the content of the controls
			// o reset them to the default value
			@Override
			public void onClick(View v) 
			{
				mEditTextName.setText("");
				mEditTextDeveloper.setText("");
				Calendar calendar = Calendar.getInstance();
				mDatePickerDate.updateDate(calendar.get(Calendar.YEAR),
									   calendar.get(Calendar.MONTH)+1,
									   calendar.get(Calendar.DAY_OF_MONTH)
									  );
				mEditTextURL.setText("");
			}
		});
		
		Button acceptButton = (Button) parentActivity.findViewById(R.id.button_form_accept);
		acceptButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.DAY_OF_MONTH, mDatePickerDate.getDayOfMonth());
				calendar.set(Calendar.MONTH, mDatePickerDate.getMonth()-1);
				calendar.set(Calendar.YEAR, mDatePickerDate.getYear());
				
				String name = mEditTextName.getText().toString();
				String developer = mEditTextDeveloper.getText().toString();
				String url = mEditTextURL.getText().toString();
				
				Intent intent = new Intent(parentActivity, ResultsActivity.class);
				
				// Store the data in the intent bundle
				intent.putExtra("name", name);
				intent.putExtra("developer", name);
				intent.putExtra("date", mDatePickerDate.getDayOfMonth() + "/" + mDatePickerDate.getMonth() + "/" + mDatePickerDate.getYear());
				intent.putExtra("url", name);
				
			
				// Store the data for the App Singleton
				MyApplication.getInstance().setData(AppData.getInstance(name, developer, calendar, url));
				
				// Store the data in the database
				DatabaseAdapter databaseAdapter = new DatabaseAdapter(parentActivity);
				databaseAdapter.open();
				databaseAdapter.insertApp(name, developer, calendar, url);
				databaseAdapter.close();
				
				// Indicating that the activity is finished by the use of the button
				((FormAppActivity)parentActivity).setButtonPressed();
				
				startActivity(intent);
				// As we have finished with this activity, we finalized it so 
				// the user never returns to it when the back button is pressed
				parentActivity.finish();
			}
		});
		
		Button cancelButton = (Button) parentActivity.findViewById(R.id.button_form_cancel);
		cancelButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				// Finishing the activity by the use of the button
				((FormAppActivity)parentActivity).setButtonPressed();
				parentActivity.finish();
			}
		});
	}
}