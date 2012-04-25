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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAdapter 
{
	// Constants for storing the the names for the database, tables, columns...
	private static final String DB_NAME = "exemple.db";
	private static final int DB_VERSION = 1;
	private static final String DB_TABLE_FORM = "form";
	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_DEVELOPER = "developer";
	public static final String KEY_DATE = "date";
	public static final String KEY_URL = "url";
	
	// Constant for creating the table for storing the form's data
	private static final String DATABASE_CREATE_FORM =
		"create table " + DB_TABLE_FORM + "("
		+ KEY_ID +" integer primary key, "
		+ KEY_NAME + " text not null, "
		+ KEY_DEVELOPER + " text not null, "
		+ KEY_DATE + " text not null, "
		+ KEY_URL + " text not null);";
	
	// We will need the references for the helper and the database in 
	// some methods, so we store them in fields
	private SQLiteDatabase mDatabase;
	private DatabaseHelper mHelper;
	
	/** 
	 * Creates a DatabaseAdapter object for the current context
	 * 
	 * @param _context The context where the database is opened
	 */
	public DatabaseAdapter(Context _context)
	{
		// When constructing the object, we obtain the helper's instance 
		mHelper = new DatabaseHelper(_context);
	}
	
	/**
	 * Opens the database. Should be called before calling any other method in this class
	 */
	public void open()
	{
		mDatabase = mHelper.getWritableDatabase();
	}
	
	/**
	 * Closes the database. Should be called once we don't need access to the database anymore
	 * and before closing the activity/service/wathever-the-context where the database has been
	 * opened
	 */
	public void close()
	{
		mDatabase.close();
	}
	
	/**
	 * Method for knowing the database status
	 * 
	 * @return Returns true if the database is opened, or false if it's closed
	 */
	public boolean isOpen()
	{
		if (mDatabase == null)
		{
			return false;
		}
		return mDatabase.isOpen();
	}
	
	/** 
	 * Method for inserting an application's data from the form to the database
	 * 
	 * @param _name 		The application's name
	 * @param _developer	The developer's name
	 * @param _calendar		A date
	 * @param _url			The url where the application is found
	 */
	public void insertApp(String _name, String _developer, Calendar _calendar, String _url)
	{	
		// We use the ContentValues object in order to store the data
		// that is going to be inserted, and also we relate each value 
		// with the column where it should be stored
		ContentValues contentValues = new ContentValues();
		
		int day = _calendar.get(Calendar.DAY_OF_MONTH);
		int month = _calendar.get(Calendar.MONTH);
		int year = _calendar.get(Calendar.YEAR);
		String date = day + "/" + month + "/" + year;
		
		contentValues.put(KEY_NAME, _name);
		contentValues.put(KEY_DEVELOPER, _developer);
		contentValues.put(KEY_DATE, date);
		contentValues.put(KEY_URL, _url);
		
		// The database provides the method insert() for inserting registers
		// in a table
		mDatabase.insert(DB_TABLE_FORM, null, contentValues);
	}
	
	/**
	 * Queries the database and obtains the register from the table "form" which matches the id
	 * 
	 * @param _id	The ID of the register we are searching for
	 * @return The cursor with the desired id, if exists, or an empty cursor otherwise
	 */
	// _id column value matches the _id parameter
	public Cursor getFormRegister(long _id)
	{
		String where = "_id=" + Long.toString(_id);
		
		return mDatabase.query(DB_TABLE_FORM, null, where, null, null, null, null);
	}
	
	/**
	 * Queries the database and obtains all the registers
	 * 
	 * @return The cursor with all the registers, if any
	 */
	public Cursor getAllFormRegisters()
	{
		String[] selectColumns = {KEY_ID, KEY_NAME, KEY_DEVELOPER, KEY_DATE};
		
		return mDatabase.query(DB_TABLE_FORM, selectColumns, null, null, null, null, null);
	}
	
	// The helper class for opening, creating and upgrading the Database 
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		public DatabaseHelper(Context _context)
		{
			super(_context, DB_NAME, null, DB_VERSION);
		}

		// This method is called if the database is not created when the method
		// getReadableDatabse() or the method getWriteableDatabase() are called
		@Override
		public void onCreate(SQLiteDatabase _database) 
		{
			_database.execSQL(DATABASE_CREATE_FORM);
		}

		// Empty method as we are at the first version of our app, so we do not
		// need to upgrade it
		@Override
		public void onUpgrade(SQLiteDatabase _database, int _oldVersion, int _newVersion) 
		{
			
		}
	}
}
