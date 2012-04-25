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

import org.uab.deic.uabdroid.solutions.unit3.entities.AppData;

import android.app.Application;

public class MyApplication extends Application 
{
	// A static field for store the singleton of MyApplication
	private static MyApplication mSingleton;
	// The data we want to share between Activities or other classes like Services
	private AppData mData;
	
	// When the instance of MyApplication is created, we store
	// the reference of itself (this pointer) in the singleton field
	public MyApplication()
	{
		super();
		mSingleton = this;
	}

	// Static method for recovering the instance of MyApplication wherever is needed
	public static MyApplication getInstance() 
	{
		return mSingleton;
	}

	// Getter and setter for accessing the data
	public AppData getData() 
	{
		return mData;
	}

	public void setData(AppData _data) 
	{
		mData = _data;
	}
}
