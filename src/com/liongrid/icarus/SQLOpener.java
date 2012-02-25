package com.liongrid.icarus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLOpener extends SQLiteOpenHelper {
	
	
	private static final int ver = 2;
	private static final String CREATE_RESULT_TABLE = "CREATE TABLE flight(" +
			"ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"timestamp date default CURRENT_TIMESTAMP, " +
			"nanoduration LONG NOT NULL," +
			"uploaded INT default 0" +
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
