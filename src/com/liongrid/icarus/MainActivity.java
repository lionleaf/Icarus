package com.liongrid.icarus;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity{
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		
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
	}
	
	public void flyClicked(View v){
		Intent i = new Intent(v.getContext(), FlyActivity.class);
		startActivity(i);
		
	}
	
	public void myFlightsClicked(View v){
		Intent i = new Intent(v.getContext(), FlightListingActivity.class);
		startActivity(i);
	}
}
