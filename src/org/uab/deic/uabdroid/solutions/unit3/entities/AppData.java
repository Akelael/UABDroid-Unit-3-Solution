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

package org.uab.deic.uabdroid.solutions.unit3.entities;

import java.util.Calendar;

public class AppData 
{
	private String mName;
	private String mDeveloper;
	private Calendar mCalendar;
	private String mUrl;
	
	// Private constructors in order to enforce the use of the factory method
	private AppData() 
	{
		this("","",Calendar.getInstance(),"");
	}
	
	private AppData(String _name, String _developer, Calendar _calendar, String _url)
	{
		mName = _name;
		mDeveloper = _developer;
		mCalendar = _calendar;
		mUrl = _url;
	}
	
	// Factory method for obtaining an object instance
	public static AppData getInstance(String _name, String _developer, Calendar _calendar, String _url)
	{
		return new AppData(_name, _developer, _calendar, _url);
	}

	// Getters
	public String getName() 
	{
		return mName;
	}

	public String getDeveloper() 
	{
		return mDeveloper;
	}

	public Calendar getCalendar() 
	{
		return mCalendar;
	}

	public String getUrl() 
	{
		return mUrl;
	}
}
