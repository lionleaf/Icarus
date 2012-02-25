package com.liongrid.icarus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLOpener extends SQLiteOpenHelper {
	
	
	private static final int ver = 1;
	private static final String CREATE_RESULT_TABLE = "CREATE TABLE result(" +
			"ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"nanoduration LONG NOT NULL " +
			");";
			
	public SQLOpener(Context context) {
		super(context, "IcarusDB", null, ver);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_RESULT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
