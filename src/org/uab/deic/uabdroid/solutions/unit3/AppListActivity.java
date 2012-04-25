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

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class AppListActivity extends ListActivity 
{
	private DatabaseAdapter mDatabaseAdapter;
	
	// This activity shows a list with the form table content
	@Override
	public void onCreate(Bundle _savedInstanceState) 
	{
		super.onCreate(_savedInstanceState);
		
		// if we have an instance of the DatabaseAdapter,
		// we do not need another
		if (mDatabaseAdapter == null)
		{
			mDatabaseAdapter = new DatabaseAdapter(this);
		}
		mDatabaseAdapter.open();
		
		fillTheList();
		
		ListView list = getListView();
		
		// Set the listener for when an item is clicked
		list.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> _parentView, View _view, int _position, long _id) 
			{
				Intent intent = new Intent(getBaseContext(), ResultsActivity.class);
				// We add the _id to the intent in order to tell the ResultsActivity that it was called by 
				// this Activity and not by the FormFragment, and also for telling it which register from
				// the database should load
				intent.putExtra(DatabaseAdapter.KEY_ID, _id);
				startActivity(intent);
			}
		});
	}
	
	private void fillTheList()
	{
		// if the database is opened, we do not try to open it a second time
		if (!mDatabaseAdapter.isOpen())
		{
			mDatabaseAdapter.open();
		}
		
		// Obtain the cursor with all the registers
		Cursor cursor = mDatabaseAdapter.getAllFormRegisters();
		// we let the activity manage the cursor in place of ourselves
		startManagingCursor(cursor);
	
		// we need to create the map between the columns of the cursor and the 
		// the views where the data is going to be stored
		String[] columnsFrom = {DatabaseAdapter.KEY_NAME,DatabaseAdapter.KEY_DEVELOPER, DatabaseAdapter.KEY_DATE};
		int[] viewsTo = {R.id.item_textview_result_name, R.id.item_textview_result_developer, R.id.item_textview_result_date};
	
		// We create the cursor adapter
		SimpleCursorAdapter cursorAdapter = 
				new SimpleCursorAdapter(this, R.layout.results_list_item, cursor, columnsFrom, viewsTo);
	
		// and set it to the ListView
		setListAdapter(cursorAdapter);
	}

	@Override
	protected void onDestroy() 
	{
		mDatabaseAdapter.close();
		super.onDestroy();
	}
}
