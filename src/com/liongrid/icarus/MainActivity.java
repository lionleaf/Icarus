package com.liongrid.icarus;

import org.holoeverywhere.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity{
	
	public static final String ADMOB_ID = "a150810ce5f0929";
	
	private static TextView totairtime,totthrows;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		
		totthrows = (TextView) findViewById(R.id.totthrows);
		totairtime = (TextView) findViewById(R.id.totairtime);
		
		// Restore preferences
        SharedPreferences prefs  = getSharedPreferences(StaticVars.PREFS_NAME, 0);
        StaticVars.UID = prefs.getLong("UID", -1);
        
        if(StaticVars.UID == -1){
        	StaticVars.UID = (long) (Math.random() * 9223372036854775807L);
        	
            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong("UID", StaticVars.UID);

            // Commit the edits!
            editor.commit();
            
        }
        //Inserting info into statisticboxes
        insertStats();
	}
	


	private void insertStats(){
            String query = "SELECT COUNT(*), SUM(nanoduration) FROM flight;";
    		SQLOpener sqlOpen = new SQLOpener(this);
    		SQLiteDatabase db = sqlOpen.getReadableDatabase();
    		Cursor result = db.rawQuery(query, null);
    		result.moveToFirst();
    		db.close();
    		totthrows.setText(Integer.toString((int)result.getInt(0)));
    		totairtime.setText(ResultManager.formatTime(result.getLong(1)));
    }
	public void flyClicked(View v){
		Intent i = new Intent(v.getContext(), FlyActivity.class);
		startActivity(i);
		
	}
	
	public void myFlightsClicked(View v){
		Intent i = new Intent(v.getContext(), FlightListingActivity.class);
		startActivity(i);
	}
	
	public void show_hsClicked(View v){
		Intent i = new Intent(v.getContext(), ViewHighscoresActivity.class);
		startActivity(i);
	}
	public void helpClicked(View v){
		Intent i = new Intent(v.getContext(), HelpActivity.class);
		startActivity(i);
	}
}
