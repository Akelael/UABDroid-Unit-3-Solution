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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity 
{	
    @Override
    public void onCreate(Bundle _savedInstanceState) 
    {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button) findViewById(R.id.button_form_activity);
        button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{
				startActivity(new Intent(getBaseContext(), AppListActivity.class));
			}
        });
        
        button = (Button) findViewById(R.id.button_add_application);
        button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{
				startActivity(new Intent(getBaseContext(), FormAppActivity.class));
			}
        });
        
    }

	@Override
	public boolean onCreateOptionsMenu(Menu _menu) 
	{
		super.onCreateOptionsMenu(_menu);
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_mainactivity, _menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem _item) 
	{
		switch(_item.getItemId())
		{
			case R.id.item_form_activity:
			{
				startActivity(new Intent(this, FormAppActivity.class));
				break;
			}
			default:
			{
				break;
			}
		}
		return true;
	}
}