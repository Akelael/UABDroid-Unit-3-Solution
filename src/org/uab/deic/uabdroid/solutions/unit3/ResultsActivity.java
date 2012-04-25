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
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class ResultsActivity extends Activity 
{
	@Override
	public void onCreate(Bundle _savedInstanceState) 
	{
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.results_layout);
		
		long id = 0;
		String name = "";
		String developer = "";
		String url = "";
		String date = "";
		
		Intent intent = getIntent();
		
		// Depending of which Activity called this one (we can know if the ID extra in the intent exists),
		// we should populate the textviews from the singleton/the intent bundle, or from the database
		// (id the ID does not exists, we are in the first case, otherwise we are in the second one)
		if ((id = intent.getLongExtra(DatabaseAdapter.KEY_ID, -1)) == -1)
		{
			// Loading the data from the Application Singleton
			/*
			AppData applicationData = MyApplication.getInstance().getData();
			
			name = applicationData.getName();
			developer = applicationData.getDeveloper();
			url = applicationData.getUrl();
			
			Calendar calendar = applicationData.getCalendar();
			date = calendar.get(Calendar.DAY_OF_MONTH) + "/" + 
					calendar.get(Calendar.MONTH) + "/" + 
					calendar.get(Calendar.YEAR);
			*/
			
			// Loading the data from the intent bundle
			name = intent.getStringExtra("name");
			developer = intent.getStringExtra("developer");
			date = intent.getStringExtra("date");
			url = intent.getStringExtra("url");
		}
		else
		{
			// Loading the data from the database
			DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
			databaseAdapter.open();
			Cursor cursor = databaseAdapter.getFormRegister(id);
			cursor.moveToFirst();
			name = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_NAME));
			developer = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_DEVELOPER));
			date = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_DATE));
			url = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_URL));
			cursor.close();
			databaseAdapter.close();
		}
		
		TextView text = (TextView)findViewById(R.id.textview_result_name);
		text.setText(name);
		text = (TextView)findViewById(R.id.textview_result_developer);
		text.setText(developer);
		text = (TextView)findViewById(R.id.textview_result_date);
		text.setText(date);
		text = (TextView)findViewById(R.id.textview_result_url);
		text.setText(url);
	}
}
